package com.narely.feedbackjourney.home.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase): ViewModel() {
    private val _uiState: MutableStateFlow<LoginViewState> = MutableStateFlow(LoginViewState())
    val uiState: StateFlow<LoginViewState> = _uiState

    fun updateUiState(uiState: LoginViewState) {
        _uiState.value = uiState
    }

    fun updateUiEmail(newEmail: String) {
        updateUiState(
            uiState.value.copy(email = newEmail)
        )
    }

    fun updateUiPassword(newPassword: String) {
        updateUiState(
            uiState.value.copy(password = newPassword)
        )
    }

    fun login() {
        val request = LoginRequest(email = uiState.value.email,
                            password = uiState.value.password
                        )
        viewModelScope.launch {
            try {
                val response = loginUseCase.invoke(request = request)
                updateUiState(
                    uiState.value.copy(token = response.token)
                )
            } catch (e: Exception) {
                updateUiState(
                    uiState.value.copy(errorMessage = e.message)
                )
            }
        }
    }
}