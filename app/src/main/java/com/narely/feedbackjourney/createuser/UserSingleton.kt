package com.narely.feedbackjourney.createuser

import com.narely.feedbackjourney.createuser.UserType.valueOf
import java.util.UUID


object UserSingleton {
    var listUser: MutableList<UserData> = mutableListOf()

    fun readUser(id: String?): UserData? {
        val user = listUser.find { it.id == id }
        return user
    }
    fun createUser(name: String, email: String, password: String, type: String, pdmEmail: String?) {
        val id = UUID.randomUUID().toString()
        val pdmId = listUser.find { it.email == pdmEmail }?.id
        val userType = valueOf(value = type)

        listUser.add(UserData(id, name, email, password, userType, pdmId))
    }

    fun deleteUser(id: String) {
        val user = listUser.find { it.id == id }
        listUser.remove(user)
    }

    fun editUser(id: String, newName: String, newEmail: String, newPassword: String, newType: String, newPdmEmail: String?) {
        val user = listUser.find { it.id == id }
        val newPdmId = listUser.find { it.email == newPdmEmail }?.id
        val newUserType = valueOf(newType)
        val newUser = listUser[listUser.indexOf(user)].copy(name = newName,
                                                            email = newEmail,
                                                            password = newPassword,
                                                            userType = newUserType,
                                                            pdmId = newPdmId)


        listUser[listUser.indexOf(user)] = newUser
    }

    fun getEmailById(id: String?): String? {
        val userEmail = listUser.find { it.id == id }?.email
        return userEmail
    }

    fun getListPdm(): List<String> {
        val user = listUser.filter { it.userType == UserType.PDM }
        val listUserEmail: MutableList<String> = mutableListOf()
        user.forEach {
            listUserEmail.add(it.email)
        }
        return listUserEmail
    }

    fun isFormValid(name: String, email: String, password: String, userType: String?, pdmEmail: String?): Boolean {
        val isFormValidLabel = !(name.isEmpty() || email.isEmpty() || password.isEmpty() ||
                userType.isNullOrEmpty())
        val showPdmList = if (isCollaborator(userType)) {
            isFormValidLabel && !pdmEmail.isNullOrEmpty()
        } else {
            isFormValidLabel
        }
        return showPdmList
    }

    fun isCollaborator(userType: String?): Boolean {
        return if (userType.isNullOrEmpty()) {
            false
        } else {
            valueOf(userType) == UserType.Collaborator
        }
    }
}