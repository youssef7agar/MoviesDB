package com.example.moviesdb.presentation.viewstate

import com.example.moviesdb.presentation.model.MultiViewItem

sealed class MoviesViewState {

    object Loading : MoviesViewState()

    object NoInternet : MoviesViewState()

    data class Success(val movies: List<MultiViewItem>, val loadingMore: Boolean = false) : MoviesViewState()

    object Error : MoviesViewState()
}