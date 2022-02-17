package com.example.moviesdb.remote.mapper

import com.example.moviesdb.domain.model.MovieResult
import com.example.moviesdb.remote.model.MovieResultRemote
import javax.inject.Inject

class MovieResultMapper @Inject constructor(
    private val movieMapper: MovieMapper
) {

    fun map(movieResultRemote: MovieResultRemote): MovieResult {
        assertEssentialParams(movieResultRemote)

        return MovieResult(
            page = movieResultRemote.page!!,
            results = movieResultRemote.results!!.map(movieMapper::map)
        )
    }

    private fun assertEssentialParams(movieResultRemote: MovieResultRemote) {
        when {
            movieResultRemote.page == null -> {
                throw RuntimeException("The params: ${movieResultRemote.page} is missing in received object: $movieResultRemote")
            }
            movieResultRemote.results == null -> {
                throw RuntimeException("The params: ${movieResultRemote.results} is missing in received object: $movieResultRemote")
            }
        }
    }
}