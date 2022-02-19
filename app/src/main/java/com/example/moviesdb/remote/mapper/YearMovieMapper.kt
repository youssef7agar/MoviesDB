package com.example.moviesdb.remote.mapper

import com.example.moviesdb.common.Constants.Companion.BASE_IMAGE_URL
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.model.YearMovie
import com.example.moviesdb.remote.model.MovieRemote
import javax.inject.Inject

class YearMovieMapper @Inject constructor() {

    fun map(movieRemote: List<MovieRemote>): List<YearMovie> {
        movieRemote.forEach(::assertEssentialParams)

        return movieRemote.groupBy { it.releaseDate!!.take(4) }
            .map {
                YearMovie(
                    year = it.key,
                    movies = it.value.map(::mapMovie).toMutableList()
                )
            }
    }

    private fun mapMovie(movieRemote: MovieRemote): Movie {

        return Movie(
            title = movieRemote.title!!,
            poster = BASE_IMAGE_URL + movieRemote.poster!!,
            id = movieRemote.id!!,
            year = movieRemote.releaseDate!!.take(4)
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