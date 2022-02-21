package com.example.moviesdb.presentation.mapper

import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.presentation.model.MovieUiModel
import javax.inject.Inject

class MovieUiMapper @Inject constructor() {

    fun map(movie: Movie, watchlist: List<Long>): MovieUiModel {

        return MovieUiModel(
            id = movie.id,
            title = movie.title,
            poster = movie.poster,
            year = movie.year,
            overview = movie.overview,
            inWatchlist = watchlist.contains(movie.id)
        )
    }
}