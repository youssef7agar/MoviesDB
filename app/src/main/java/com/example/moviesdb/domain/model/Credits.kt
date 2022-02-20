package com.example.moviesdb.domain.model

data class Credits(
    val cast: List<CastMember>,
    val crew: List<CrewMember>
)