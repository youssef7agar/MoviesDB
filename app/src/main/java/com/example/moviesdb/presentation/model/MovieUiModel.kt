package com.example.moviesdb.presentation.model

data class MovieUiModel(
    val id: Long,
    val title: String,
    val poster: String,
    val year: String,
    val overview: String,
    val inWatchlist: Boolean
)