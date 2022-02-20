package com.example.moviesdb.presentation.viewstate

import com.example.moviesdb.presentation.model.MovieUiModel

sealed class SimilarMoviesViewState {

    object Error : SimilarMoviesViewState()

    object Loading : SimilarMoviesViewState()

    object NoInternet : SimilarMoviesViewState()

    data class Success(val movies: List<MovieUiModel>) : SimilarMoviesViewState()
}