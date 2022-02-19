package com.example.moviesdb.presentation.viewstate

sealed class MoviesViewEvent {

    object Error : MoviesViewEvent()

    object NoInternet : MoviesViewEvent()
}