package com.example.moviesdb.presentation.viewstate

import com.example.moviesdb.presentation.model.YearMovieUiModel

sealed class MoviesViewState {

    object Loading : MoviesViewState()

    object NoInternet : MoviesViewState()

    data class Success(val movies: List<YearMovieUiModel>, val loadingMore: Boolean = false) : MoviesViewState()

    object Error : MoviesViewState()
}