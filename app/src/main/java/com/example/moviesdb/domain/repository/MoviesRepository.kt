package com.example.moviesdb.domain.repository

import com.example.moviesdb.remote.api.MoviesApiService
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: MoviesApiService
) {
}