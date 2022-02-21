package com.example.moviesdb.remote.model

import com.google.gson.annotations.SerializedName

data class MovieRemote(
    @SerializedName("id") val id: Long?,
    @SerializedName("title") val title: String?,
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("overview") val overview: String?
)