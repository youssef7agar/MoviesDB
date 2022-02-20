package com.example.moviesdb.domain.model

data class MovieDetails(
    val title: String,
    val backImage: String,
    val overview: String,
    val releaseDate: String,
    val revenue: Int,
    val status: String,
    val tagline: String
)