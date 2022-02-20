package com.example.moviesdb.remote.model

import com.google.gson.annotations.SerializedName

data class CastMemberRemote(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("profile_path") val photo: String?,
    @SerializedName("known_for_department") val department: String?,
    @SerializedName("popularity") val popularity: Float?
)