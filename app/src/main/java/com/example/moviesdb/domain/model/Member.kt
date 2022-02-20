package com.example.moviesdb.domain.model

data class Member(
    val id: Long,
    val name: String,
    val photo: String,
    val department: String,
    val popularity: Float
)