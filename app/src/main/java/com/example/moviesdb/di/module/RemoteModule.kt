package com.example.moviesdb.di.module

import com.example.moviesdb.remote.api.MoviesApiService
import dagger.Module
import retrofit2.Retrofit

@Module
class RemoteModule {

    companion object {
        fun provideMoviesApiService(retrofit: Retrofit): MoviesApiService {
            return retrofit.create(MoviesApiService::class.java)
        }
    }
}