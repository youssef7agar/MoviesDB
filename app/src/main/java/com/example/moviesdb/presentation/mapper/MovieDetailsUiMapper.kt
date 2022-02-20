package com.example.moviesdb.presentation.mapper

import com.example.moviesdb.domain.model.MovieDetails
import com.example.moviesdb.presentation.model.MovieDetailsUiModel
import javax.inject.Inject

class MovieDetailsUiMapper @Inject constructor() {

    fun map(movieDetails: MovieDetails): MovieDetailsUiModel {

        return MovieDetailsUiModel(
             title = movieDetails.title,
         backImage = movieDetails.backImage,
         overview = movieDetails.overview,
         releaseDate = movieDetails.releaseDate,
         revenue = movieDetails.revenue.toString(),
         status = movieDetails.status,
         tagline = movieDetails.tagline
        )
    }
}