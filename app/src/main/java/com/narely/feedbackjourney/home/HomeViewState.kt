package com.narely.feedbackjourney.home

import com.narely.feedbackjourney.core.model.UserDataModel

data class HomeViewState(val list: MutableList<UserDataModel> = mutableListOf(),
                         val isLoading: Boolean = false,
                         val currentUser: UserDataModel? = null)