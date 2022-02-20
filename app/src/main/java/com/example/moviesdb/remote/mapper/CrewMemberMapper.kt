package com.example.moviesdb.remote.mapper

import com.example.moviesdb.common.Constants.Companion.BASE_IMAGE_URL
import com.example.moviesdb.domain.model.Member
import com.example.moviesdb.remote.model.CrewMemberRemote
import javax.inject.Inject

class CrewMemberMapper @Inject constructor() {

    fun map(remote: CrewMemberRemote): Member {
        assertEssentialParams(remote)

        return Member(
            name = remote.name!!,
            photo = BASE_IMAGE_URL + remote.photo!!,
            department = remote.department!!,
            popularity = remote.popularity!!
        )
    }

    private fun assertEssentialParams(remote: CrewMemberRemote) {
        when {
            remote.name == null -> {
                throw RuntimeException("The params: remote.name is missing in received object: $remote")
            }
            remote.photo == null -> {
                throw RuntimeException("The params: remote.photo is missing in received object: $remote")
            }
            remote.department == null -> {
                throw RuntimeException("The params: remote.department is missing in received object: $remote")
            }
            remote.popularity == null -> {
                throw RuntimeException("The params: remote.popularity is missing in received object: $remote")
            }
        }
    }
}