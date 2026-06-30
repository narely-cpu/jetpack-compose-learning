package com.narely.feedbackjourney.createuser

import java.util.UUID


object UserSingleton {
    var listUser: MutableList<UserData> = mutableListOf()

    fun readUser(id: String?): UserData? {
        val user = listUser.find { it.id == id }
        return user
    }
    fun createUser(name: String, email: String, password: String, pdmEmail: String?) {
        val id = UUID.randomUUID().toString()
        val pdmId = listUser.find { it.email == pdmEmail }?.id
        listUser.add(UserData(id, name, email, password, pdmId))
//        listUser.add(UserData(id, name, email, password, userType, pdmId))
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

    fun getPdmEmailById(id: String?): String? {
        val userEmail = listUser.find { it.id == id }?.email
        return userEmail
    }
}