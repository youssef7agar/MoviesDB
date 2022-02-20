package com.example.moviesdb.presentation.viewstate

import com.example.moviesdb.presentation.model.MovieDetailsUiModel

sealed class DetailsViewState{

    object Loading : DetailsViewState()

    object Error : DetailsViewState()

    object NoInternet : DetailsViewState()

    data class Success(val movieDetails: MovieDetailsUiModel) : DetailsViewState()
}