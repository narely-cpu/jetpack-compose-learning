package com.narely.feedbackjourney.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narely.feedbackjourney.features.managementuser.domain.GetUsersUseCase
import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.managementuser.domain.RemoveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getUsersUseCase: GetUsersUseCase,
    val removeUserUseCase: RemoveUserUseCase,
): ViewModel() {
    private val _uiState: MutableStateFlow<HomeViewState> =
        MutableStateFlow(HomeViewState())
    val uiState: StateFlow<HomeViewState> = _uiState

    private fun updateUiState(uiState: HomeViewState) {
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

    fun updateCurrentUser(user: UserResponse) {
        updateUiState(
            uiState.value.copy(currentUser = user)
        )
    }

    fun removeUser(id: Int) = viewModelScope.launch {
       removeUserUseCase.invoke(id)
    }
}