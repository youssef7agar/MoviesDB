package com.example.moviesdb.presentation.viewstate

sealed class MovieDetailsViewEvent {

    object Error : MovieDetailsViewEvent()

    object NoInternet : MovieDetailsViewEvent()
}