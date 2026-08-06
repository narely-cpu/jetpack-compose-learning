package com.narely.feedbackjourney.home.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.invoke

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

    fun updateUiToken(newToken: String?) {
        updateUiState(
            uiState.value.copy(token = newToken)
        )
    }

    fun updateUiErrorMessage(newErrorMessage: String?) {
        updateUiState(
            uiState.value.copy(errorMessage = newErrorMessage)
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