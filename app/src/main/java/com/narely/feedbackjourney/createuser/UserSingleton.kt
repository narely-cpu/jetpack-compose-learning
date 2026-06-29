package com.narely.feedbackjourney.createuser

import java.util.UUID


object UserSingleton {
    var listUser: MutableList<UserData> = mutableListOf()

    fun createUser(name: String, email: String, password: String) {
        val id = UUID.randomUUID().toString()
        listUser.add(UserData(id, name, email, password))
    }

    fun deleteUser(id: String) {
        val user = listUser.find { it.id == id }
        listUser.remove(user)
    }
}