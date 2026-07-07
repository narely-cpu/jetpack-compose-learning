package com.narely.feedbackjourney.core.data

import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserSingleton
import com.narely.feedbackjourney.core.model.UserType
import com.narely.feedbackjourney.core.model.UserType.valueOf
import com.narely.feedbackjourney.createuser.CreateEditUserViewModel
import com.narely.feedbackjourney.createuser.CreateEditUserViewState
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class UsersRepository() {

    val listUser = com.narely.feedbackjourney.core.data.UserSingleton.listUser

    fun getUsers(): List<UserDataModel> {
        return listUser
    }

    fun getUser(userId: String?): UserDataModel? {
        val user = listUser.find { it.id == userId }
        return user
    }

    fun createUser(value: CreateEditUserViewState) {
        val id = UUID.randomUUID().toString()
        val userType = valueOf(value = value.userType)

        val userModel = UserDataModel(
            id,
            value.name,
            value.email,
            value.password,
            userType,
            value.pdmEmail
        )

        listUser.add(userModel)
    }

    fun removeUser(userId: String) {
        val user = listUser.find { it.id == userId }
        listUser.remove(user)
    }

    fun updateUser(value: CreateEditUserViewState) {
        val user = listUser.find { it.id == value.id }
        if (user != null) {
            val newUserType = valueOf(value.userType)
            val newUser = listUser[listUser.indexOf(user)]
                .copy(name = value.name,
                    email = value.email,
                    password = value.password,
                    userType = newUserType,
                    pdmEmail = value.pdmEmail
                )
            listUser[listUser.indexOf(user)] = newUser
        }
    }

    fun getListPdm(): List<String> {
        val user = listUser.filter { it.userType == UserType.PDM }
        val listUserEmail: MutableList<String> = mutableListOf()
        user.forEach {
            listUserEmail.add(it.email)
        }
        return listUserEmail
    }
}