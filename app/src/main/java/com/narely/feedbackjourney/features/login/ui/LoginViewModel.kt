package com.narely.feedbackjourney.features.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narely.feedbackjourney.features.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase): ViewModel() {
    private val _uiState: MutableStateFlow<LoginViewState> = MutableStateFlow(LoginViewState())
    val uiState: StateFlow<LoginViewState> = _uiState

    private fun updateUiState(uiState: LoginViewState) {
        _uiState.value = uiState
    }

    private fun updateUiToken(newToken: String?) {
        updateUiState(
            uiState.value.copy(token = newToken)
        )
    }

    private fun updateUiErrorMessage(newErrorMessage: String?) {
        updateUiState(
            uiState.value.copy(errorMessage = newErrorMessage)
        )
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

    fun login() = viewModelScope.launch {
        loginUseCase.invoke(
            email = uiState.value.email,
            password = uiState.value.password,
            tokenResponse = { updateUiToken(newToken = it) },
            errorMessage = { updateUiErrorMessage(newErrorMessage = it) }
        )
    }
}