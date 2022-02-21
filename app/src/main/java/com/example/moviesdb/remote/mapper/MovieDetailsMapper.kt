package com.example.moviesdb.remote.mapper

import com.example.moviesdb.common.Constants.Companion.BASE_IMAGE_URL
import com.example.moviesdb.domain.model.MovieDetails
import com.example.moviesdb.remote.model.MovieDetailsRemote
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor() {

    fun map(remote: MovieDetailsRemote): MovieDetails {
        assertEssentialParams(remote)

        return MovieDetails(
            id = remote.id!!,
            title = remote.title!!,
            backImage = BASE_IMAGE_URL + remote.backImage,
            overview = remote.overview ?: "",
            releaseDate = remote.releaseDate ?: "",
            revenue = remote.revenue ?: 0,
            status = remote.status ?: "",
            tagline = remote.tagline ?: ""
        )
    }

    private fun assertEssentialParams(movieRemote: MovieDetailsRemote) {
        when {
            movieRemote.id == null -> {
                throw RuntimeException("The params: movieRemote.id is missing in received object: $movieRemote")
            }
            movieRemote.title == null -> {
                throw RuntimeException("The params: movieRemote.title is missing in received object: $movieRemote")
            }
            movieRemote.backImage == null -> {
                throw RuntimeException("The params: movieRemote.backImage is missing in received object: $movieRemote")
            }
        }
    }
}