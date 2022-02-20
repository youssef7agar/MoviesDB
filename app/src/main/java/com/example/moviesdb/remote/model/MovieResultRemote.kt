package com.example.moviesdb.remote.model

import com.google.gson.annotations.SerializedName

data class MovieResultRemote(
    @SerializedName("results") val results: List<MovieRemote>?
)