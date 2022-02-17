package com.example.moviesdb.remote.model

import com.google.gson.annotations.SerializedName

data class MovieRemote(
    @SerializedName("title") val title: String?,
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("overview") val overview: String?
)