package com.example.moviesdb.presentation.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesdb.common.Event
import com.example.moviesdb.domain.model.YearMovie
import com.example.moviesdb.domain.usecase.GetPopularMoviesUseCase
import com.example.moviesdb.domain.usecase.SearchMoviesUseCase
import com.example.moviesdb.presentation.mapper.YearMovieUiMapper
import com.example.moviesdb.presentation.viewstate.MoviesViewEvent
import com.example.moviesdb.presentation.viewstate.MoviesViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val yearMovieUiMapper: YearMovieUiMapper
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    private val _viewState = MutableLiveData<MoviesViewState>()
    val viewState: LiveData<MoviesViewState>
        get() = _viewState

    private val _viewEvent = MutableLiveData<Event<MoviesViewEvent>>()
    val viewEvent: LiveData<Event<MoviesViewEvent>>
        get() = _viewEvent

    private var movies = mutableListOf<YearMovie>()

    init {
        MoviesViewState.Loading.also(_viewState::setValue)
        getPopularMovies()
    }

    fun getPopularMovies() = launch {
        movies.clear()
        _viewState.postValue(MoviesViewState.Loading)
        when (val result = getPopularMoviesUseCase()) {
            is GetPopularMoviesUseCase.Result.Success -> {
                updateMoviesList(newList = result.movieResult.results)
                _viewState.postValue(
                    MoviesViewState.Success(
                        movies = movies.map(yearMovieUiMapper::map)
                    )
                )
            }
            is GetPopularMoviesUseCase.Result.Error -> {
                Timber.w(result.throwable)
                _viewState.postValue(MoviesViewState.Error)
                _viewEvent.postValue(Event(MoviesViewEvent.Error))
            }
        }
    }

    fun nextPage() = launch {
        _viewState.postValue((viewState.value as MoviesViewState.Success).copy(loadingMore = true))
        when (val result = getPopularMoviesUseCase.next()) {
            is GetPopularMoviesUseCase.Result.Success -> {
                updateMoviesList(newList = result.movieResult.results)
                _viewState.postValue(
                    MoviesViewState.Success(
                        movies = movies.map(yearMovieUiMapper::map)
                    )
                )
            }
            is GetPopularMoviesUseCase.Result.Error -> {
                _viewEvent.postValue(Event(MoviesViewEvent.Error))
                Timber.w(result.throwable)
            }
        }
    }

    fun search(query: String) = launch {
        _viewState.postValue(MoviesViewState.Loading)
        if (query.isNotEmpty()) {
            movies.clear()
            when (val result = searchMoviesUseCase(query)) {
                is SearchMoviesUseCase.Result.Success -> {
                    updateMoviesList(newList = result.movieResult.results)
                    _viewState.postValue(
                        MoviesViewState.Success(
                            movies = movies.map(yearMovieUiMapper::map)
                        )
                    )
                }
                is SearchMoviesUseCase.Result.Error -> {
                    Timber.w(result.throwable)
                    _viewState.postValue(MoviesViewState.Error)
                    _viewEvent.postValue(Event(MoviesViewEvent.Error))
                }
            }
        } else {
            getPopularMovies()
        }
    }

    private fun updateMoviesList(newList: List<YearMovie>) {
        newList.forEach { newYearMovie ->
            if (yearsList().contains(newYearMovie.year)) {
                movies.forEach { oldYearMovie ->
                    if (oldYearMovie.year == newYearMovie.year) {
                        oldYearMovie.movies.addAll(newYearMovie.movies)
                    }
                }
            } else {
                movies.add(newYearMovie)
            }
        }
    }

    private fun yearsList(): List<String> {
        return movies.map { it.year }
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}