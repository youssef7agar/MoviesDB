package com.example.moviesdb.presentation.model

data class YearAndMoviesUiModel(
    val year: YearUiModel,
    val movies: List<MovieUiModel>
)