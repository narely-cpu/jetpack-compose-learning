package com.narely.feedbackjourney.createuser

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateEditUserViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<CreateEditUserViewState> = MutableStateFlow(CreateEditUserViewState())
    val uiState: StateFlow<CreateEditUserViewState> = _uiState

    fun updateUiState(uiState: CreateEditUserViewState) {
        _uiState.value = uiState
    }

    fun updateUiName(newName: String) {
        updateUiState(
            uiState.value.copy(name = newName)
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
    fun updateUiUserType(newUserType: String) {
        updateUiState(
            uiState.value.copy(userType = newUserType)
        )
    }
    fun updateUiPdmId(newPdmId: String) {
        updateUiState(
            uiState.value.copy(pdmId = newPdmId)
        )
    }
}