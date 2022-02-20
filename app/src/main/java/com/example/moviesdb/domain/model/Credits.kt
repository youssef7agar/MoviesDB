package com.example.moviesdb.domain.model

data class Credits(
    val cast: List<Member>,
    val crew: List<Member>
)