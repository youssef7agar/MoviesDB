package com.example.moviesdb.presentation.mapper

import com.example.moviesdb.domain.model.Member
import com.example.moviesdb.presentation.model.MemberUiModel
import javax.inject.Inject

class MemberUiMapper @Inject constructor() {

    fun map(member: Member): MemberUiModel {

        return MemberUiModel(
            id = member.id,
            name = member.name,
            photo = member.photo
        )
    }
}