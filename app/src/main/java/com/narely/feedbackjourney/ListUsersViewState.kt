package com.narely.feedbackjourney

import androidx.compose.runtime.MutableState
import com.narely.feedbackjourney.createuser.UserDataModel

data class ListUsersViewState(val list: MutableList<UserDataModel> = mutableListOf(),
                              val isLoading: Boolean = false,
                              val currentUser: UserDataModel? = null)