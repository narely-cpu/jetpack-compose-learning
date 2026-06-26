package com.narely.feedbackjourney

import androidx.lifecycle.ViewModel
import com.narely.feedbackjourney.createuser.CreateUserSingleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListUsersViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<ListUsersViewState> = MutableStateFlow(ListUsersViewState())
    val uiState: StateFlow<ListUsersViewState> = _uiState

    fun updateUiState(uiState: ListUsersViewState) {
        _uiState.value = uiState
    }

    fun updateList() {
        updateUiState(uiState.value.copy(list = CreateUserSingleton.listUser))
    }
}