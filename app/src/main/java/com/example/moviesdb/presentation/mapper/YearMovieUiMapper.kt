package com.example.moviesdb.presentation.mapper

import com.example.moviesdb.domain.model.YearMovie
import com.example.moviesdb.presentation.model.YearMovieUiModel
import javax.inject.Inject

class YearMovieUiMapper @Inject constructor(
    private val movieUiMapper: MovieUiMapper
) {

    fun map(yearMovie: YearMovie, watchlist: List<Long>): YearMovieUiModel {

        return YearMovieUiModel(
            year = yearMovie.year,
            movies = yearMovie.movies.map { movie ->
                movieUiMapper.map(movie, watchlist)
            }
        )
    }
}