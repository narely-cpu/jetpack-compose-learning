package com.narely.feedbackjourney.features.managementuser.ui

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse

data class ManagementUserState(
    val listUsers: List<UserResponse> = emptyList<UserResponse>(),
    val isLoading: Boolean = false,
    val currentUser: UserResponse? = null,
    val showModal: Boolean = false
)