package com.narely.feedbackjourney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narely.feedbackjourney.createuser.UserDataModel
import com.narely.feedbackjourney.createuser.UserSingleton.listUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class ListUsersViewModel(val getUsersUseCase: GetUsersUseCase): ViewModel() {
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
                list = getUsersUseCase.invoke(),
                isLoading = false
            )
        )
    }

    fun updateCurrentUser(user: UserDataModel) {
        updateUiState(
            uiState.value.copy(currentUser = user)
        )
    }

    fun deleteUser(id: String) {
//        removeUserUseCase.invocke(id)
//        val user = getUsersUseCase.invoke().find { it.id == id }
//        listUser.remove(user)
    }
}