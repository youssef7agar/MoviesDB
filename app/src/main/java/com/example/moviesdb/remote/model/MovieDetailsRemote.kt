package com.example.moviesdb.remote.model

import com.google.gson.annotations.SerializedName

data class MovieDetailsRemote (
    @SerializedName("title") val title: String?,
    @SerializedName("backdrop_path") val backImage: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("revenue") val revenue: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("tagline") val tagline: String?
)
