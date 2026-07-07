package com.narely.feedbackjourney.createuser

import androidx.lifecycle.ViewModel
import com.narely.feedbackjourney.createuser.UserSingleton.listUser
import com.narely.feedbackjourney.createuser.UserType.valueOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class CreateEditUserViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<CreateEditUserViewState> = MutableStateFlow(CreateEditUserViewState())
    val uiState: StateFlow<CreateEditUserViewState> = _uiState
    fun updateUiState(uiState: CreateEditUserViewState) {
        _uiState.value = uiState
    }

    fun updateUiId(newId: String) {
        updateUiState(
            uiState.value.copy(id = newId)
        )
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
        val user = listUser.find { it.id == id }
        return user
    }

    fun createUser() {
        val id = UUID.randomUUID().toString()
        updateUiId(newId = id)
        val userType = valueOf(value = uiState.value.userType)
        listUser.add(UserDataModel(id,
            uiState.value.name,
            uiState.value.email,
            uiState.value.password,
            userType,
            uiState.value.pdmEmail)
        )
    }

    fun editUser() {
        val user = listUser.find { it.id == uiState.value.id }
        val newUserType = valueOf(uiState.value.userType)
        val newUser = listUser[listUser.indexOf(user)]
            .copy(name = uiState.value.name,
                email = uiState.value.email,
                password = uiState.value.password,
                userType = newUserType,
                pdmEmail = uiState.value.pdmEmail
            )

        listUser[listUser.indexOf(user)] = newUser
    }

    fun getListPdm(): List<String> {
        val user = listUser.filter { it.userType == UserType.PDM }
        val listUserEmail: MutableList<String> = mutableListOf()
        user.forEach {
            listUserEmail.add(it.email)
        }
        return listUserEmail
    }

    fun isFormValid(): Boolean {
        val isFormValidLabel = !(uiState.value.name.isEmpty() ||
                uiState.value.email.isEmpty() ||
                uiState.value.password.isEmpty() ||
                uiState.value.userType.isEmpty())
        val showPdmList = if (isCollaborator()) {
            isFormValidLabel && uiState.value.pdmEmail.isNotEmpty()
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