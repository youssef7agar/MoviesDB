package com.example.moviesdb.domain.usecase

import com.example.moviesdb.common.Constants.Companion.PAGINATION_START
import com.example.moviesdb.common.unwrapResult
import com.example.moviesdb.domain.model.MovieResult
import com.example.moviesdb.domain.repository.MoviesRepository
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    private val page = AtomicInteger(PAGINATION_START)

    suspend operator fun invoke(): Result {
        page.set(PAGINATION_START)

        return repo.getPopularMovies(page.get()).unwrapResult({ movieResult ->
            if (movieResult.results.isNotEmpty())
                page.incrementAndGet()
            Result.Success(movieResult)
        }, { exception ->
            Result.Error(exception)
        })
    }

    suspend fun next(): Result {

        return repo.getPopularMovies(page.get()).unwrapResult({ movieResult ->
            if (movieResult.results.isNotEmpty())
                page.incrementAndGet()
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