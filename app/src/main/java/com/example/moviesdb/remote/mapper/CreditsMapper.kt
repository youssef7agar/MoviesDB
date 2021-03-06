package com.example.moviesdb.remote.mapper

import com.example.moviesdb.domain.model.Credits
import com.example.moviesdb.remote.model.CreditsRemote
import javax.inject.Inject

class CreditsMapper @Inject constructor(
    private val castMemberMapper: CastMemberMapper,
    private val crewMemberMapper: CrewMemberMapper
) {

    fun map(remote: CreditsRemote): Credits {

        return Credits(
            cast = remote.cast!!.map(castMemberMapper::map),
            crew = remote.crew!!.map(crewMemberMapper::map)
        )
    }

    private fun assertEssentialParams(remote: CreditsRemote) {
        when {
            remote.cast == null -> {
                throw RuntimeException("The params: CreditsRemote.title is missing in received object: $remote")
            }
            remote.crew == null -> {
                throw RuntimeException("The params: CreditsRemote.id is missing in received object: $remote")
            }
        }
    }
}