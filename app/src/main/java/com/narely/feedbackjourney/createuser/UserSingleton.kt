package com.narely.feedbackjourney.createuser

import java.util.UUID


object UserSingleton {
    var listUser: MutableList<UserDataModel> = mutableListOf()

    fun readUser(id: String?): UserDataModel? {
        val user = listUser.find { it.id == id }
        return user
    }
    fun createUser(name: String, email: String, password: String) {
        val id = UUID.randomUUID().toString()
        listUser.add(UserDataModel(id, name, email, password))
    }

    fun deleteUser(id: String) {
        val user = listUser.find { it.id == id }
        listUser.remove(user)
    }

    fun editUser(id: String, newName: String, newEmail: String, newPassword: String) {
        val user = listUser.find { it.id == id }
        val newUser = listUser[listUser.indexOf(user)].copy(name = newName, email = newEmail, password = newPassword)
        listUser[listUser.indexOf(user)] = newUser
    }
}