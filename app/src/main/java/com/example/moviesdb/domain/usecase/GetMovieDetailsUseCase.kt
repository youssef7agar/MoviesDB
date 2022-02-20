package com.example.moviesdb.domain.usecase

import com.example.moviesdb.common.unwrapResult
import com.example.moviesdb.domain.model.MovieDetails
import com.example.moviesdb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repo: MoviesRepository
) {

    suspend operator fun invoke(movieId: Long): Result {

        return repo.getMovieDetails(movieId).unwrapResult({ movieDetails ->
            Result.Success(movieDetails)
        }, { exception ->
            Result.Error(exception)
        })
    }

    sealed class Result {

        data class Success(val movieDetails: MovieDetails) : Result()

        data class Error(val throwable: Throwable) : Result()
    }
}