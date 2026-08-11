package com.narely.feedbackjourney.features.createedituser.ui

import com.narely.feedbackjourney.features.createedituser.domain.model.UserDataModel

data class CreateEditUserViewState(
    var collaborator: UserDataModel = UserDataModel(),
    val errorMessage: String? = "",
    val isLoading: Boolean = false,
    val listPdm: List<UserDataModel>? = emptyList(),
    val pdm: UserDataModel? = null,
)
