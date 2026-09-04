package com.narely.feedbackjourney.features.managementuser.ui

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.createedituser.domain.model.UserDataModel

data class ManagementUserState(
    val listUsers: List<UserResponse> = emptyList<UserResponse>(),
    val currentUser: UserResponse? = null,
    val showModal: Boolean = false,
    var collaborator: UserDataModel = UserDataModel(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val listPdm: List<UserDataModel>? = emptyList(),
    val pdm: UserDataModel? = null,
    val showAlert: Boolean = false
)