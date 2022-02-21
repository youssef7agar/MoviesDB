package com.example.moviesdb.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesdb.common.Preferences
import com.example.moviesdb.domain.model.MovieDetails
import com.example.moviesdb.domain.usecase.GetCreditsUseCase
import com.example.moviesdb.domain.usecase.GetMovieDetailsUseCase
import com.example.moviesdb.domain.usecase.GetSimilarMoviesUseCase
import com.example.moviesdb.presentation.mapper.MemberUiMapper
import com.example.moviesdb.presentation.mapper.MovieDetailsUiMapper
import com.example.moviesdb.presentation.mapper.MovieUiMapper
import com.example.moviesdb.presentation.viewstate.CreditsViewState
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
    private val movieUiMapper: MovieUiMapper,
    private val memberUiMapper: MemberUiMapper,
    private val preferences: Preferences
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    private val _detailsViewState = MutableLiveData<DetailsViewState>()
    val detailsViewState: LiveData<DetailsViewState>
        get() = _detailsViewState

    private val _similarMoviesViewState = MutableLiveData<SimilarMoviesViewState>()
    val similarMoviesViewState: LiveData<SimilarMoviesViewState>
        get() = _similarMoviesViewState

    private val _creditsViewState = MutableLiveData<CreditsViewState>()
    val creditsViewState: LiveData<CreditsViewState>
        get() = _creditsViewState

    private lateinit var similarIds: List<Long>
    private lateinit var currentMovieDetails: MovieDetails

    private val watchlist: MutableList<Long>
        get() = preferences.getWatchList().map { it.toLong() }.toMutableList()

    init {
        DetailsViewState.Loading.also(_detailsViewState::setValue)
        SimilarMoviesViewState.Loading.also(_similarMoviesViewState::setValue)
        CreditsViewState.Loading.also(_creditsViewState::setValue)
    }

    fun getMovieDetails(movieId: Long) = launch {
        _detailsViewState.postValue(DetailsViewState.Loading)

        when (val result = getMovieDetailsUseCase(movieId)) {
            is GetMovieDetailsUseCase.Result.Success -> {
                currentMovieDetails = result.movieDetails
                _detailsViewState.postValue(
                    DetailsViewState.Success(
                        movieDetailsUiMapper.map(
                            currentMovieDetails,
                            watchlist
                        )
                    )
                )
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
                similarIds = result.movies.movies.map { it.id }
                _similarMoviesViewState.postValue(
                    SimilarMoviesViewState.Success(
                        result.movies.movies.map { movie ->
                            movieUiMapper.map(movie, watchlist)
                        }
                    )
                )
            }
            is GetSimilarMoviesUseCase.Result.Error -> {
                _similarMoviesViewState.postValue(SimilarMoviesViewState.Error)
                Timber.w("Something went wrong when loading the similar movies: ${result.throwable}")
            }
        }
    }

    fun getCredits(movieId: Long) = launch {
        _creditsViewState.postValue(CreditsViewState.Loading)

        when (val result = getCreditsUseCase(movieId)) {
            is GetCreditsUseCase.Result.Success -> {
                _creditsViewState.postValue(
                    CreditsViewState.Success(
                        actors = result.credits.cast.map(memberUiMapper::map),
                        directors = result.credits.crew.map(memberUiMapper::map)
                    )
                )
            }
            is GetCreditsUseCase.Result.Error -> {
                _creditsViewState.postValue(CreditsViewState.Error)
                Timber.w("Something went wrong when loading the credits: ${result.throwable}")
            }
        }
    }

    fun watchList() {
        if (watchlist.contains(currentMovieDetails.id)) {
            val newList = watchlist.also { it.remove(currentMovieDetails.id) }
            preferences.addWatchList(newList.map { it.toString() })
        } else {
            val newList = watchlist.also { it.add(currentMovieDetails.id) }
            preferences.addWatchList(newList.map { it.toString() })
        }
        _detailsViewState.postValue(
            DetailsViewState.Success(
                movieDetailsUiMapper.map(
                    currentMovieDetails,
                    watchlist
                )
            )
        )
    }
}