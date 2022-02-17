package com.example.moviesdb.domain.repository

import com.example.moviesdb.common.Result
import com.example.moviesdb.domain.model.MovieResult
import com.example.moviesdb.remote.api.MoviesApiService
import com.example.moviesdb.remote.mapper.MovieResultMapper
import java.lang.Exception
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: MoviesApiService,
    private val movieResultMapper: MovieResultMapper
) {

    suspend fun getPopularMovies(page: Int): Result<MovieResult> {
        return try {
            Result.Success(movieResultMapper.map(api.getPopularMovies(page)))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}