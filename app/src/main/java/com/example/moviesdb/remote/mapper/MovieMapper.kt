package com.example.moviesdb.remote.mapper

import com.example.moviesdb.common.Constants.Companion.BASE_IMAGE_URL
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.remote.model.MovieRemote
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun map(remote: MovieRemote): Movie {
        assertEssentialParams(remote)

        return Movie(
            id = remote.id!!,
            poster = BASE_IMAGE_URL + remote.poster,
            year = remote.releaseDate!!,
            title = remote.title!!,
            overview = remote.overview ?: ""
        )
    }

    private fun assertEssentialParams(movieRemote: MovieRemote) {
        when {
            movieRemote.title == null -> {
                throw RuntimeException("The params: movieRemote.title is missing in received object: $movieRemote")
            }
            movieRemote.id == null -> {
                throw RuntimeException("The params: movieRemote.id is missing in received object: $movieRemote")
            }
            movieRemote.poster == null -> {
                throw RuntimeException("The params: movieRemote.poster is missing in received object: $movieRemote")
            }
            movieRemote.releaseDate == null -> {
                throw RuntimeException("The params: movieRemote.releaseDate is missing in received object: $movieRemote")
            }
        }
    }
}