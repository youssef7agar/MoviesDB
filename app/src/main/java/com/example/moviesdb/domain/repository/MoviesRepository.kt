package com.example.moviesdb.domain.repository

import com.example.moviesdb.common.Result
import com.example.moviesdb.common.tryCall
import com.example.moviesdb.domain.model.Credits
import com.example.moviesdb.domain.model.MovieDetails
import com.example.moviesdb.domain.model.MovieResult
import com.example.moviesdb.domain.model.Movies
import com.example.moviesdb.remote.api.MoviesApiService
import com.example.moviesdb.remote.mapper.CreditsMapper
import com.example.moviesdb.remote.mapper.MovieDetailsMapper
import com.example.moviesdb.remote.mapper.MovieResultMapper
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: MoviesApiService,
    private val movieResultMapper: MovieResultMapper,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val creditsMapper: CreditsMapper
) {

    suspend fun getPopularMovies(page: Int): Result<MovieResult> {
        return tryCall {
            Result.Success(movieResultMapper.mapYearMovie(api.getPopularMovies(page)))
        }
    }

    suspend fun searchMovies(query: String): Result<MovieResult> {
        return tryCall {
            Result.Success(movieResultMapper.mapYearMovie(api.searchMovies(query)))
        }
    }

    suspend fun getMovieDetails(movieId: Long): Result<MovieDetails> {
        return tryCall {
            Result.Success(movieDetailsMapper.map(api.getMovieDetails(movieId)))
        }
    }

    suspend fun getSimilarMovies(movieId: Long): Result<Movies> {
        return tryCall {
            Result.Success(movieResultMapper.mapMovie(api.getSimilarMovies(movieId)))
        }
    }

    suspend fun getMoviesCredits(movieId: Long): Result<Credits> {
        return tryCall {
            Result.Success(creditsMapper.map(api.getCredits(movieId)))
        }
    }
}