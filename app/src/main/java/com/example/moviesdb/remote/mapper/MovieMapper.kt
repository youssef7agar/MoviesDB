package com.example.moviesdb.remote.mapper

import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.remote.model.MovieRemote
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun map(movieRemote: MovieRemote): Movie {
        assertEssentialParams(movieRemote)

        return Movie(
            title = movieRemote.title!!,
            poster = movieRemote.poster!!,
            overview = movieRemote.overview!!
        )
    }

    private fun assertEssentialParams(movieRemote: MovieRemote) {
        when {
            movieRemote.title == null -> {
                throw RuntimeException("The params: ${movieRemote.title} is missing in received object: $movieRemote")
            }
            movieRemote.overview == null -> {
                throw RuntimeException("The params: ${movieRemote.overview} is missing in received object: $movieRemote")
            }
            movieRemote.poster == null -> {
                throw RuntimeException("The params: ${movieRemote.poster} is missing in received object: $movieRemote")
            }
        }
    }
}