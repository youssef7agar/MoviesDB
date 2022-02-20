package com.example.moviesdb.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesdb.common.Event
import com.example.moviesdb.domain.usecase.GetCreditsUseCase
import com.example.moviesdb.domain.usecase.GetMovieDetailsUseCase
import com.example.moviesdb.domain.usecase.GetSimilarMoviesUseCase
import com.example.moviesdb.presentation.mapper.MovieDetailsUiMapper
import com.example.moviesdb.presentation.model.MovieDetailsUiModel
import com.example.moviesdb.presentation.viewstate.MovieDetailsViewEvent
import com.example.moviesdb.presentation.viewstate.DetailsViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val movieDetailsUiMapper: MovieDetailsUiMapper
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    private val _viewState = MutableLiveData<DetailsViewState>()
    val viewState: LiveData<DetailsViewState>
        get() = _viewState

    private val _viewEvent = MutableLiveData<Event<MovieDetailsViewEvent>>()
    val viewEvent: LiveData<Event<MovieDetailsViewEvent>>
        get() = _viewEvent

    init {
        DetailsViewState.Loading.also(_viewState::setValue)
    }

    fun getMovieDetails(movieId: Long) = launch {
        _viewState.postValue(DetailsViewState.Loading)

        when (val result = getMovieDetailsUseCase(movieId)) {
            is GetMovieDetailsUseCase.Result.Success -> {
                _viewState.postValue(DetailsViewState.Success(movieDetailsUiMapper.map(result.movieDetails)))
            }
            is GetMovieDetailsUseCase.Result.Error -> {
                _viewState.postValue(DetailsViewState.Error)
                Timber.w("Something went wrong when loading the movie details: ${result.throwable}")
            }
        }
    }
}