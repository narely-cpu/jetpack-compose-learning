package com.narely.feedbackjourney.features.createedituser.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narely.feedbackjourney.features.createedituser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.createedituser.domain.CreateUserUseCase
import com.narely.feedbackjourney.features.createedituser.domain.EditUserUseCase
import com.narely.feedbackjourney.features.createedituser.domain.GetListPdmUseCase
import com.narely.feedbackjourney.features.createedituser.domain.GetUserUseCase
import com.narely.feedbackjourney.features.createedituser.domain.model.UserTypeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEditUserViewModel @Inject constructor(
    val createUserUseCase: CreateUserUseCase,
    val editUserUseCase: EditUserUseCase,
    val getUserUseCase: GetUserUseCase,
    val getListPdmUseCase: GetListPdmUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<CreateEditUserViewState> = MutableStateFlow(CreateEditUserViewState())
    val uiState: StateFlow<CreateEditUserViewState> = _uiState

    private fun updateUiState(uiState: CreateEditUserViewState) {
        _uiState.value = uiState
    }

    private fun updateUiCollaborator(newCollaborator: UserDataModel) {
        updateUiState(
            uiState.value.copy(collaborator = newCollaborator)
        )
    }

    private fun updateUiPdm(newPdm: UserDataModel?) {
        updateUiState(
            uiState.value.copy(pdm = newPdm)
        )
    }

    private fun updateUiListPdm(newListPdm: List<UserDataModel>?) {
        updateUiState(
            uiState.value.copy(listPdm = newListPdm)
        )
    }

    private fun getPdmUser() {
        val pdm = uiState.value.listPdm?.find { it.email == uiState.value.collaborator.pdmEmail }
        updateUiPdm(newPdm = pdm)
    }

    private fun areMandatoryFieldsFilled(): Boolean {
        val areMandatoryFieldsFilled =
            uiState.value.collaborator.name.isNotEmpty() &&
                    uiState.value.collaborator.email.isNotEmpty() &&
                    uiState.value.collaborator.type.name.isNotEmpty()

        return areMandatoryFieldsFilled
    }

    private fun hasPdmAssigned(): Boolean {
        return when (uiState.value.collaborator.type) {
            UserTypeEnum.COLLABORATOR -> uiState.value.pdm?.email?.isNotEmpty() ?: false
            UserTypeEnum.PDM -> true
            else -> true
        }
    }

    private suspend fun readUser(id: Int): UserDataModel? {
        return getUserUseCase.invoke(id = id, listPdm = uiState.value.listPdm)
    }

    fun createUser(finishedActivityCreateUser: () -> Unit) = viewModelScope.launch {
        createUserUseCase.invoke(
            collaborator = uiState.value.collaborator,
            pdm = uiState.value.pdm,
            finishedActivityCreateUser = finishedActivityCreateUser,
            errorMessage = { updateUiErrorMessage(newErrorMessage = it) }
        )
    }

    fun editUser(finishedActivityCreateUser: () -> Unit) = viewModelScope.launch {
        editUserUseCase.invoke(
            collaborator = uiState.value.collaborator,
            pdm = uiState.value.pdm,
            finishedActivityCreateUser = finishedActivityCreateUser,
            errorMessage = { updateUiErrorMessage(newErrorMessage = it) }
        )
    }

    fun getListPdm() = viewModelScope.launch {
        updateUiListPdm(newListPdm = getListPdmUseCase.invoke())
    }

    fun onCreateUiCreateEditView(userId: Int) {
        getListPdm().invokeOnCompletion {
            updateUiCurrentUser(newCurrentUserId = userId)
        }
    }

    fun updateUiName(newName: String) {
        updateUiCollaborator(
            uiState.value.collaborator.copy(name = newName)
        )
    }

    fun updateUiEmail(newEmail: String) {
        updateUiCollaborator(
            uiState.value.collaborator.copy(email = newEmail)
        )
    }

    fun updateUiUserType(newUserType: String) {
        updateUiCollaborator(
            uiState.value.collaborator.copy(type = UserTypeEnum.valueOf(newUserType))
        )
    }

    fun updateUiPdmEmail(newPdmEmail: String) {
        updateUiCollaborator(
            uiState.value.collaborator.copy(pdmEmail = newPdmEmail)
        )
        getPdmUser()
    }

    fun updateUiCurrentUser(newCurrentUserId: Int) = viewModelScope.launch {
        val newCurrentUser = readUser(newCurrentUserId)

        if (newCurrentUser != null) {
            updateUiCollaborator(newCollaborator = newCurrentUser)
        }
    }

    fun updateUiErrorMessage(newErrorMessage: String?) {
        updateUiState(
            uiState.value.copy(errorMessage = newErrorMessage)
        )
    }

    fun isButtonEnable(): Boolean {
        return areMandatoryFieldsFilled() && hasPdmAssigned()
    }

    fun isCollaborator(): Boolean {
        return uiState.value.collaborator.type == UserTypeEnum.COLLABORATOR
    }
}