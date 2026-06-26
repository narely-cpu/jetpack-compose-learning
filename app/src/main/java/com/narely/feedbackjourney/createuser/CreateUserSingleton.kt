package com.narely.feedbackjourney.createuser


object CreateUserSingleton {
    var listUser: MutableList<UserData> = mutableListOf()

    fun createUser(name: String, email: String, password: String) {
        listUser.add(UserData(name, email, password))
    }
}