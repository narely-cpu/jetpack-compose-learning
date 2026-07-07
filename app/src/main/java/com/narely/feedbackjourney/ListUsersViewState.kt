package com.narely.feedbackjourney

import com.narely.feedbackjourney.createuser.UserDataModel

data class ListUsersViewState(val list: List<UserDataModel> = listOf(),
                              val isLoading: Boolean = false,
                              val currentUser: UserDataModel? = null)