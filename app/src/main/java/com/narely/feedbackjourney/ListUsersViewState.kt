package com.narely.feedbackjourney

import com.narely.feedbackjourney.createuser.UserData

data class ListUsersViewState(val list: MutableList<UserData> = mutableListOf(),
                              val isLoading: Boolean = false)