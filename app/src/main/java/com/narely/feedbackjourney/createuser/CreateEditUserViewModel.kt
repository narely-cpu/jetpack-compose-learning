package com.narely.feedbackjourney.createuser

import androidx.lifecycle.ViewModel
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import com.narely.feedbackjourney.core.model.UserType.valueOf
import com.narely.feedbackjourney.createuser.domain.CreateUserUseCase
import com.narely.feedbackjourney.createuser.domain.EditUserUseCase
import com.narely.feedbackjourney.createuser.domain.GetListPdmUseCase
import com.narely.feedbackjourney.createuser.domain.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class CreateEditUserViewModel(val createUserUseCase: CreateUserUseCase,
                              val editUserUseCase: EditUserUseCase,
                              val getUserUseCase: GetUserUseCase,
                              val getListPdmUseCase: GetListPdmUseCase): ViewModel() {
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

    fun updateUiCurrentUser(newCurrentUserId: String?) {
        val newCurrentUser = readUser(newCurrentUserId)
        if (newCurrentUser != null) {
            updateUiState(
                uiState.value.copy(id = newCurrentUser.id,
                    name = newCurrentUser.name,
                    email = newCurrentUser.email,
                    password = newCurrentUser.password,
                    userType = newCurrentUser.userType.userValue,
                    pdmEmail = newCurrentUser.pdmEmail ?: "",
                )
            )
        }
    }

    fun readUser(id: String?): UserDataModel? {
        return getUserUseCase.invoke(id)
    }

    fun createUser() {
        createUserUseCase.invoke(
            name = uiState.value.name,
            email = uiState.value.email,
            password = uiState.value.password,
            userType = valueOf(uiState.value.userType),
            pdmEmail = uiState.value.pdmEmail
        )
    }

    fun editUser() {
        uiState.value.id?.let {
            editUserUseCase.invoke(
                id = it,
                name = uiState.value.name,
                email = uiState.value.email,
                password = uiState.value.password,
                userType = valueOf(uiState.value.userType),
                pdmEmail = uiState.value.pdmEmail
            )
        }
    }

    fun getListPdm(): List<String> {
        return getListPdmUseCase.invoke()
    }

    fun isFormValid(): Boolean {
        val isFormValidLabel = !(uiState.value.name.isEmpty() ||
                uiState.value.email.isEmpty() ||
                uiState.value.password.isEmpty() ||
                uiState.value.userType.isEmpty())
        val showPdmList = if (isCollaborator()) {
            isFormValidLabel && uiState.value.pdmEmail.isNullOrEmpty()
        } else {
            isFormValidLabel
        }
        return showPdmList
    }

    fun isCollaborator(): Boolean {
        return if (uiState.value.userType.isEmpty()) {
            false
        } else {
            valueOf(uiState.value.userType) == UserType.Collaborator
        }
    }
}