package com.narely.feedbackjourney.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narely.feedbackjourney.core.domain.GetUsersUseCase
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.home.domain.RemoveUserUseCase
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
    fun updateUiState(uiState: HomeViewState) {
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
        removeUserUseCase.invoke(id)
    }
}