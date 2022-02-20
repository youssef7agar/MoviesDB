package com.example.moviesdb.domain.model

data class YearMovie(
    val year: String,
    val movies: MutableList<Movie>
)