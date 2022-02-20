package com.example.moviesdb.remote.model

import com.google.gson.annotations.SerializedName

data class CrewMemberRemote(
    @SerializedName("name") val name: String?,
    @SerializedName("profile_path") val photo: String?,
    @SerializedName("department") val department: String?,
    @SerializedName("popularity") val popularity: Float?
)