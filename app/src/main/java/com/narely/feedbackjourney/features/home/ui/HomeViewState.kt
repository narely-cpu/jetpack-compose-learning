package com.narely.feedbackjourney.features.home.ui

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse

data class HomeViewState(
    val list: List<UserResponse> = emptyList<UserResponse>(),
    val isLoading: Boolean = false,
    val currentUser: UserResponse? = null
)