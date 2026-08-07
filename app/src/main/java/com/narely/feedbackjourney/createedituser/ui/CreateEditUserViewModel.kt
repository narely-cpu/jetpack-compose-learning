package com.narely.feedbackjourney.createedituser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narely.feedbackjourney.createedituser.domain.model.UserDataModel
import com.narely.feedbackjourney.createedituser.ui.UserTypeEnum.valueOf
import com.narely.feedbackjourney.createedituser.domain.CreateUserUseCase
import com.narely.feedbackjourney.createedituser.domain.EditUserUseCase
import com.narely.feedbackjourney.createedituser.domain.GetListPdmUseCase
import com.narely.feedbackjourney.createedituser.domain.GetUserUseCase
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

    fun updateUiPdmEmail(newPdmEmail: String) {
        updateUiState(
            uiState.value.copy(pdmEmail = newPdmEmail)
        )
    }

    fun updateUiListPdm(newListPdm: List<String>?) {
        updateUiState(
            uiState.value.copy(listPdm = newListPdm)
        )
    }

    fun updateUiCurrentUser(newCurrentUserId: Int) = viewModelScope.launch {
        val newCurrentUser = readUser(newCurrentUserId)
        if (newCurrentUser != null) {
            updateUiState(
                uiState.value.copy(
                    id = newCurrentUser.id,
                    name = newCurrentUser.name,
                    email = newCurrentUser.email,
                    password = newCurrentUser.password,
                    userType = newCurrentUser.type,
                    pdmEmail = newCurrentUser.pdmEmail,
                )
            )
        }
    }

    fun updateUiErrorMessage(newErrorMessage: String?) {
        updateUiState(
            uiState.value.copy(errorMessage = newErrorMessage)
        )
    }

    suspend fun readUser(id: Int): UserDataModel? {
        return getUserUseCase.invoke(id)
    }

    fun createUser(finishedActivityCreateUser: () -> Unit) = viewModelScope.launch {
        createUserUseCase.invoke(
            name = uiState.value.name,
            email = uiState.value.email,
            userType = uiState.value.userType,
            pdmEmail = uiState.value.pdmEmail,
            finishedActivityCreateUser = finishedActivityCreateUser,
            errorMessage = { updateUiErrorMessage(newErrorMessage = it) }
        )
    }

    fun editUser(finishedActivityCreateUser: () -> Unit) = viewModelScope.launch {
        uiState.value.id?.let { it ->
            editUserUseCase.invoke(
                id = it,
                name = uiState.value.name,
                email = uiState.value.email,
                userType = uiState.value.userType,
                pdmEmail = uiState.value.pdmEmail,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = { updateUiErrorMessage(newErrorMessage = it) }
            )
        }
    }

    fun getListPdm() = viewModelScope.launch {
        updateUiListPdm(getListPdmUseCase.invoke())
    }

    fun areMandatoryFieldsFilled(): Boolean {
        val areMandatoryFieldsFilled =
                uiState.value.name.isNotEmpty() &&
                uiState.value.email.isNotEmpty() &&
                uiState.value.password.isNotEmpty() &&
                uiState.value.userType.isNotEmpty()

        return areMandatoryFieldsFilled
    }

    fun needPDMAssignedOrIsEmptyPdmEmailField(): Boolean {
        return when (uiState.value.userType) {
            UserTypeEnum.COLLABORATOR.userValue -> uiState.value.pdmEmail.isNullOrEmpty()
            UserTypeEnum.PDM.userValue -> false
            else -> false
        }
    }

    fun isButtonEnable(): Boolean {
        return areMandatoryFieldsFilled() && (!needPDMAssignedOrIsEmptyPdmEmailField())
    }

    fun isCollaborator(): Boolean {
        return if (uiState.value.userType.isEmpty()) {
            false
        } else {
            valueOf(uiState.value.userType) == UserTypeEnum.COLLABORATOR
        }
    }
}