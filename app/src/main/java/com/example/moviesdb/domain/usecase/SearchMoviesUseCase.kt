package com.example.moviesdb.domain.usecase

import com.example.moviesdb.common.unwrapResult
import com.example.moviesdb.domain.model.MovieResult
import com.example.moviesdb.domain.repository.MoviesRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    suspend operator fun invoke(query: String): Result {
        return repo.searchMovies(query).unwrapResult({ movieResult ->
            Result.Success(movieResult)
        }, { exception ->
            Result.Error(exception)
        })
    }

    sealed class Result {

        data class Success(val movieResult: MovieResult) : Result()

        data class Error(val throwable: Throwable) : Result()
    }
}