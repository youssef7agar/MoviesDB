package com.example.moviesdb.remote.api

import com.example.moviesdb.common.Constants.Companion.MOVIE_CREDITS_ENDPOINT
import com.example.moviesdb.common.Constants.Companion.MOVIE_DETAILS_ENDPOINT
import com.example.moviesdb.common.Constants.Companion.POPULAR_MOVIES_ENDPOINT
import com.example.moviesdb.common.Constants.Companion.SEARCH_MOVIES_ENDPOINT
import com.example.moviesdb.common.Constants.Companion.SIMILAR_MOVIES_ENDPOINT
import com.example.moviesdb.remote.model.CreditsRemote
import com.example.moviesdb.remote.model.MovieDetailsRemote
import com.example.moviesdb.remote.model.MovieResultRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET(POPULAR_MOVIES_ENDPOINT)
    suspend fun getPopularMovies(@Query("page") page: Int): MovieResultRemote

    @GET(SEARCH_MOVIES_ENDPOINT)
    suspend fun searchMovies(@Query("query") query: String): MovieResultRemote

    @GET(MOVIE_DETAILS_ENDPOINT)
    suspend fun getMovieDetails(@Path("movie_id") movieId: Long): MovieDetailsRemote

    @GET(SIMILAR_MOVIES_ENDPOINT)
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Long): MovieResultRemote

    @GET(MOVIE_CREDITS_ENDPOINT)
    suspend fun getCredits(@Path("movie_id") movieId: Long): CreditsRemote
}