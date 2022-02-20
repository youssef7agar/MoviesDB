package com.example.moviesdb.remote.model

import com.google.gson.annotations.SerializedName

class CreditsRemote(
    @SerializedName("cast") val cast: List<CastMemberRemote>?,
    @SerializedName("crew") val crew: List<CrewMemberRemote>?
)