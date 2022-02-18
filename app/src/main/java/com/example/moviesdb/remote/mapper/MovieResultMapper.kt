package com.example.moviesdb.remote.mapper

import com.example.moviesdb.domain.model.MovieResult
import com.example.moviesdb.remote.model.MovieResultRemote
import javax.inject.Inject

class MovieResultMapper @Inject constructor(
    private val yearMovieMapper: YearMovieMapper
) {

    fun map(movieResultRemote: MovieResultRemote): MovieResult {
        assertEssentialParams(movieResultRemote)

        return MovieResult(
            results = yearMovieMapper.map(movieResultRemote.results!!)
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