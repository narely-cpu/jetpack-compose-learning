package com.narely.feedbackjourney.createuser

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.uuid.Uuid

enum class UserType {
    PDM, COLLABORATOR, ADMIN
}

data class UserData(val id: String,
                    val name: String? = null,
                    val email: String? = null,
                    val password: String? = null)
//data class UserData(val id: String? = null,
//                    val name: String? = null,
//                    val email: String? = null,
//                    val password: String? = null,
//                    val type: UserType = UserType.COLLABORATOR,
//                    val pdmId: String? = null,
//                    val active: Boolean = false)
//class CreateUserViewModel: ViewModel() {
//    private val _uiUserState = MutableStateFlow(UserData())
//    val uiUserState: StateFlow<UserData> = _uiUserState.asStateFlow()
//    private val _uiNameState = MutableStateFlow(String())
//    val uiNameState: StateFlow<String> = _uiNameState.asStateFlow()
//    private val _uiEmailState = MutableStateFlow(String())
//    val uiEmailState: StateFlow<String> = _uiEmailState.asStateFlow()
//    private val _uiPasswordState = MutableStateFlow(String())
//    val uiPasswordState: StateFlow<String> = _uiPasswordState.asStateFlow()
//    private val _uiUserTypeState = MutableStateFlow(UserType.COLLABORATOR)
//    val uiUserTypeState: StateFlow<UserType> = _uiUserTypeState.asStateFlow()
//    private val _uiPDMIdState = MutableStateFlow(null)
//    val uiPDMIdState: StateFlow<String?> = _uiPDMIdState.asStateFlow()
//    private val _uiActiveUserState = MutableStateFlow(false)
//    val iActiveUserState: StateFlow<Boolean> = _uiActiveUserState.asStateFlow()
//    private val _uiListUsersState = MutableStateFlow<List<UserData>>(emptyList())
//    val uiListUsersState: StateFlow<List<UserData>> = _uiListUsersState.asStateFlow()
//
//
//    fun updateName() {
//        _uiNameState.value
//    }
//    fun create() {
//        _uiUserState.update { currentState ->
//            currentState.copy(
//                id = UUID.randomUUID().toString(),
//                name = currentState.name,
//                email = currentState.email,
//                password = currentState.password,
//                type = currentState.type,
//                pdmId = currentState.pdmId,
//                active = currentState.active
//            )
//        }
////        this.id = UUID.randomUUID().toString()
////        if (!validatePassword(password) || !validateInternalEmail(email)) {
////            return null
////        } else {
////            this.active = true
////            val user = UserData(id, name, email,
////                password, type,
////                pdmId, true
////            )
////            return user
////        }
//    }
//
//    private fun validateInternalEmail(email: String): Boolean {
//        val internalDomains: List<String> = listOf("ciandt.com", "ciandt.com.br")
//        if (!email.contains("@")) {
//            println("Invalid email")
//            return false
//        }
//        val domain = email.substring(email.indexOf("@") + 1).lowercase()
//        if (!internalDomains.contains(domain)) {
//            println("Only CI&T corporate emails (@ciandt.com) are allowed for internal users")
//            return false
//        }
//        return true
//    }
//
//    private fun validatePassword(password: String): Boolean {
//        if (password.length < 8) {
//            println("Password must be at least 8 characters long")
//            return false
//        }
//        if (!password.matches(".*[A-Z].*".toRegex())) {
//            println("Password must contain at least one uppercase letter")
//            return false
//        }
//        if (!password.matches(".*\\d.*".toRegex())) {
//            println("Password must contain at least one number")
//            return false
//        }
//        if (!password.matches(".*[!@#$%^&*()_+\\-={}:;\"',.<>/?].*".toRegex())) {
//            println("Password must contain at least one special character")
//            return false
//        }
//        return true
//    }
//
//    private fun searchUserByIndex(listUsers: MutableList<UserData>): UserData? {
//        for ((index, value) in listUsers.withIndex()) {
//            println("$index -> \"${value.name}\"")
//        }
//        val userIndexInput = readln()
//        if (userIndexInput.toDoubleOrNull() != null) {
//            return validateIndexInput(userIndexInput.toInt(), listUsers)
//        } else {
//            println("Enter the index of a valid user.")
//            return null
//        }
//    }
//
//    private fun validateIndexInput(userIndexInput: Int, listUsers: MutableList<UserData>): UserData? {
//        if (userIndexInput >= listUsers.count() || userIndexInput < 0) {
//            println("Enter the index of a valid user.")
//            return null
//        } else {
//            val userSelected = listUsers[userIndexInput]
//            print("data user: $userSelected")
//            return userSelected
//        }
//    }
//}