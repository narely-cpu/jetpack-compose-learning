package com.narely.feedbackjourney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narely.feedbackjourney.createuser.UserSingleton
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class ListUsersViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<ListUsersViewState> = MutableStateFlow(ListUsersViewState())
    val uiState: StateFlow<ListUsersViewState> = _uiState

    fun updateUiState(uiState: ListUsersViewState) {
        _uiState.value = uiState
    }
    fun updateList() = viewModelScope.launch {
        updateUiState(
            uiState.value.copy(isLoading = true)
        )

        delay(1000.milliseconds)

        updateUiState(
            uiState.value.copy(
                list = UserSingleton.listUser,
                isLoading = false
            )
        )
    }
}