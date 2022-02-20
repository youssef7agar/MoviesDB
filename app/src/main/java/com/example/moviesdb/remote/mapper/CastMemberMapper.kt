package com.example.moviesdb.remote.mapper

import com.example.moviesdb.common.Constants.Companion.BASE_IMAGE_URL
import com.example.moviesdb.domain.model.CastMember
import com.example.moviesdb.remote.model.CastMemberRemote
import javax.inject.Inject

class CastMemberMapper @Inject constructor() {

    fun map(remote: CastMemberRemote): CastMember {
        assertEssentialParams(remote)

        return CastMember(
            name = remote.name!!,
            department = remote.department!!,
            photo = BASE_IMAGE_URL + remote.photo,
            popularity = remote.popularity!!
        )
    }

    private fun assertEssentialParams(remote: CastMemberRemote) {
        when {
            remote.name == null -> {
                throw RuntimeException("The params: castMemberRemote.name is missing in received object: $remote")
            }
            remote.department == null -> {
                throw RuntimeException("The params: castMemberRemote.department is missing in received object: $remote")
            }
            remote.popularity == null -> {
                throw RuntimeException("The params: castMemberRemote.popularity is missing in received object: $remote")
            }
        }
    }
}