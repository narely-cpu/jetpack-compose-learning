package com.narely.feedbackjourney.core.data

import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import com.narely.feedbackjourney.core.model.UserType.valueOf
import com.narely.feedbackjourney.createuser.CreateEditUserViewState
import java.util.UUID

class UsersRepository() {

    val listUser = UserSingleton.listUser

    fun getUsers(): List<UserDataModel> {
        return listUser
    }

    fun getUser(userId: String?): UserDataModel? {
        val user = listUser.find { it.id == userId }
        return user
    }

    fun createUser(userModel: UserDataModel) {

        listUser.add(userModel)
    }

    fun removeUser(userId: String) {
        val user = listUser.find { it.id == userId }
        listUser.remove(user)
    }

    fun updateUser(id: String, name: String, email: String, password: String, userType: UserType, pdmEmail: String) {
        val user = listUser.find { it.id == id }
        if (user != null) {
            val newUser = listUser[listUser.indexOf(user)]
                .copy(name = name,
                    email = email,
                    password = password,
                    userType = userType,
                    pdmEmail = pdmEmail
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

private object UserSingleton {
    val listUser: MutableList<UserDataModel> = mutableListOf(
        UserDataModel(
            "sdd",
            "narely",
            "narely@ciandt.com",
            "senha",
            UserType.PDM,
            null
        )
    )
}