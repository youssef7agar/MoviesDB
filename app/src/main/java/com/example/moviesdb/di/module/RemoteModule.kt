package com.example.moviesdb.di.module

import com.example.moviesdb.remote.api.MoviesApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RemoteModule {

    companion object {
        @Provides
        @Singleton
        fun provideMoviesApiService(retrofit: Retrofit): MoviesApiService {
            return retrofit.create(MoviesApiService::class.java)
        }
    }
}