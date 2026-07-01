package com.narely.feedbackjourney

import com.narely.feedbackjourney.createuser.UserDataModel

data class ListUsersViewState(val list: MutableList<UserDataModel> = mutableListOf(),
                              val isLoading: Boolean = false)