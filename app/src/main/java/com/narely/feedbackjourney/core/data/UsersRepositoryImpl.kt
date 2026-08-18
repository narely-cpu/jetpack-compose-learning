package com.narely.feedbackjourney.core.data

import androidx.annotation.VisibleForTesting
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import javax.inject.Inject

interface UsersRepository {
    fun getUsers(): MutableList<UserDataModel>
    fun getUser(userId: String?): UserDataModel?
    fun createUser(userModel: UserDataModel)
    fun removeUser(userId: String)
    fun updateUser(id: String, name: String, email: String, password: String, userType: UserType, pdmEmail: String?)
    fun getListPdm(): List<String>
}

class UsersRepositoryImpl @Inject constructor(items: List<UserDataModel>? = null): UsersRepository {

    val listUser = items?.toMutableList() ?: UserSingleton.listUser

    override fun getUsers(): MutableList<UserDataModel> {
        return listUser
    }

    override fun getUser(userId: String?): UserDataModel? {
        val user = listUser.find { it.id == userId }
        return user
    }

    override fun createUser(userModel: UserDataModel) {
        listUser.add(userModel)
    }

    override fun removeUser(userId: String) {
        val user = listUser.find { it.id == userId }
        listUser.remove(user)
    }

    override fun updateUser(id: String, name: String, email: String, password: String, userType: UserType, pdmEmail: String?) {
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

    override fun getListPdm(): List<String> {
        val user = listUser.filter { it.userType == UserType.PDM }
        val listUserEmail: MutableList<String> = mutableListOf()
        user.forEach {
            listUserEmail.add(it.email)
        }
        return listUserEmail
    }
}

@VisibleForTesting
private object UserSingleton {
    val listUser: MutableList<UserDataModel> = mutableListOf()
}