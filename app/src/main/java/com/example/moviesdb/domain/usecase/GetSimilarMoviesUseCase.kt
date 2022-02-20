package com.example.moviesdb.domain.usecase

import com.example.moviesdb.common.unwrapResult
import com.example.moviesdb.domain.model.Movies
import com.example.moviesdb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val repo: MoviesRepository
) {

    private val maximum = 5

    suspend operator fun invoke(movieId: Long): Result {

        return repo.getSimilarMovies(movieId).unwrapResult({ movieResult ->
            Result.Success(movies = movieResult.copy(
                movies = movieResult.movies.take(maximum)
            ))
        }, { exception ->
            Result.Error(exception)
        })
    }

    sealed class Result {

        data class Success(val movies: Movies) : Result()

        data class Error(val throwable: Throwable) : Result()
    }
}