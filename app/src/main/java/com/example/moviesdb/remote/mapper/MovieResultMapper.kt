package com.example.moviesdb.remote.mapper

import com.example.moviesdb.domain.model.MovieResult
import com.example.moviesdb.domain.model.Movies
import com.example.moviesdb.remote.model.MovieResultRemote
import javax.inject.Inject

class MovieResultMapper @Inject constructor(
    private val yearMovieMapper: YearMovieMapper,
    private val movieMapper: MovieMapper
) {

    fun mapYearMovie(movieResultRemote: MovieResultRemote): MovieResult {
        assertEssentialParams(movieResultRemote)

        return MovieResult(
            results = yearMovieMapper.map(movieResultRemote.results!!)
        )
    }

    fun mapMovie(movieResultRemote: MovieResultRemote): Movies {
        assertEssentialParams(movieResultRemote)

        return Movies(
            movies = movieResultRemote.results!!.map(movieMapper::map)
        )
    }

    private fun assertEssentialParams(movieResultRemote: MovieResultRemote) {
        when (movieResultRemote.results) {
            null -> {
                throw RuntimeException("The params: ${movieResultRemote.results} is missing in received object: $movieResultRemote")
            }
        }
    }
}