package com.narely.feedbackjourney.home

import com.narely.feedbackjourney.core.model.UserDataModel

data class HomeViewState(val list: List<UserDataModel> = listOf(),
                         val isLoading: Boolean = false,
                         val currentUser: UserDataModel? = null)