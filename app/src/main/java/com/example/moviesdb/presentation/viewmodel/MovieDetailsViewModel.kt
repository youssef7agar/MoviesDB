package com.example.moviesdb.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesdb.domain.usecase.GetCreditsUseCase
import com.example.moviesdb.domain.usecase.GetMovieDetailsUseCase
import com.example.moviesdb.domain.usecase.GetSimilarMoviesUseCase
import com.example.moviesdb.presentation.mapper.MovieDetailsUiMapper
import com.example.moviesdb.presentation.mapper.MovieUiMapper
import com.example.moviesdb.presentation.viewstate.DetailsViewState
import com.example.moviesdb.presentation.viewstate.SimilarMoviesViewState
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
    private val movieDetailsUiMapper: MovieDetailsUiMapper,
    private val movieUiMapper: MovieUiMapper
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    private val _detailsViewState = MutableLiveData<DetailsViewState>()
    val detailsViewState: LiveData<DetailsViewState>
        get() = _detailsViewState

    private val _similarMoviesViewState = MutableLiveData<SimilarMoviesViewState>()
    val similarMoviesViewState: LiveData<SimilarMoviesViewState>
        get() = _similarMoviesViewState

    init {
        DetailsViewState.Loading.also(_detailsViewState::setValue)
        SimilarMoviesViewState.Loading.also(_similarMoviesViewState::setValue)
    }

    fun getMovieDetails(movieId: Long) = launch {
        _detailsViewState.postValue(DetailsViewState.Loading)

        when (val result = getMovieDetailsUseCase(movieId)) {
            is GetMovieDetailsUseCase.Result.Success -> {
                _detailsViewState.postValue(DetailsViewState.Success(movieDetailsUiMapper.map(result.movieDetails)))
            }
            is GetMovieDetailsUseCase.Result.Error -> {
                _detailsViewState.postValue(DetailsViewState.Error)
                Timber.w("Something went wrong when loading the movie details: ${result.throwable}")
            }
        }
    }

    fun getSimilarMovies(movieId: Long) = launch {
        _similarMoviesViewState.postValue(SimilarMoviesViewState.Loading)

        when (val result = getSimilarMoviesUseCase(movieId)) {
            is GetSimilarMoviesUseCase.Result.Success -> {
                _similarMoviesViewState.postValue(
                    SimilarMoviesViewState.Success(
                        result.movies.movies.take(5).map(
                            movieUiMapper::map
                        )
                    )
                )
            }
            is GetSimilarMoviesUseCase.Result.Error -> {
                _similarMoviesViewState.postValue(SimilarMoviesViewState.Error)
                Timber.w("Something went wrong when loading the similar movies: ${result.throwable}")
            }
        }
    }
}