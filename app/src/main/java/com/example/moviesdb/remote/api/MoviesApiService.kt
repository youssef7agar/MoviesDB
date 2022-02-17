package com.example.moviesdb.remote.api

import com.example.moviesdb.common.Constants.Companion.POPULAR_MOVIES_ENDPOINT
import com.example.moviesdb.remote.model.MovieResultRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET(POPULAR_MOVIES_ENDPOINT)
    suspend fun getPopularMovies(@Query("page") page: Int): MovieResultRemote
}