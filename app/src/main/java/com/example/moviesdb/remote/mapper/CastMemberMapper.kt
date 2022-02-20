package com.example.moviesdb.remote.mapper

import com.example.moviesdb.common.Constants.Companion.BASE_IMAGE_URL
import com.example.moviesdb.domain.model.CastMember
import com.example.moviesdb.remote.model.CastMemberRemote
import javax.inject.Inject

class CastMemberMapper @Inject constructor() {

    fun map(remote: CastMemberRemote): CastMember {

        return CastMember(
            name = remote.name ?: "",
            department = remote.department ?: "",
            photo = BASE_IMAGE_URL + remote.photo,
            popularity = remote.popularity!!
        )
    }
}