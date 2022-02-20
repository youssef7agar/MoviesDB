package com.example.moviesdb.domain.repository

import com.example.moviesdb.common.Result
import com.example.moviesdb.domain.model.Credits
import com.example.moviesdb.domain.model.MovieDetails
import com.example.moviesdb.domain.model.MovieResult
import com.example.moviesdb.domain.model.Movies
import com.example.moviesdb.remote.api.MoviesApiService
import com.example.moviesdb.remote.mapper.CreditsMapper
import com.example.moviesdb.remote.mapper.MovieDetailsMapper
import com.example.moviesdb.remote.mapper.MovieResultMapper
import java.lang.Exception
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: MoviesApiService,
    private val movieResultMapper: MovieResultMapper,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val creditsMapper: CreditsMapper
) {

    suspend fun getPopularMovies(page: Int): Result<MovieResult> {
        return try {
            Result.Success(movieResultMapper.mapYearMovie(api.getPopularMovies(page)))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun searchMovies(query: String): Result<MovieResult> {
        return try {
            Result.Success(movieResultMapper.mapYearMovie(api.searchMovies(query)))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getMovieDetails(movieId: Long): Result<MovieDetails> {
        return try {
            Result.Success(movieDetailsMapper.map(api.getMovieDetails(movieId)))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getSimilarMovies(movieId: Long): Result<Movies> {
        return try {
            Result.Success(movieResultMapper.mapMovie(api.getSimilarMovies(movieId)))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getMoviesCredits(movieId: Long): Result<Credits> {
        return try {
            Result.Success(creditsMapper.map(api.getCredits(movieId)))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}