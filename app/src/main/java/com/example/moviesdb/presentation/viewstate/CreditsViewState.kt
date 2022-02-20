package com.example.moviesdb.presentation.viewstate

import com.example.moviesdb.presentation.model.MemberUiModel

sealed class CreditsViewState {

    object Loading : CreditsViewState()

    object Error : CreditsViewState()

    object NoInternet : CreditsViewState()

    data class Success(val actors: List<MemberUiModel>, val directors: List<MemberUiModel>) : CreditsViewState()
}