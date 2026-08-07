package com.narely.feedbackjourney.home.ui

import com.narely.feedbackjourney.core.model.UserResponse

data class HomeViewState(
    val list: List<UserResponse> = emptyList<UserResponse>(),
    val isLoading: Boolean = false,
    val currentUser: UserResponse? = null
)