package com.narely.feedbackjourney.createuser


object UserSingleton {
    var listUser: MutableList<UserDataModel> = mutableListOf()

    fun getEmailById(id: String?): String? {
        val userEmail = listUser.find { it.id == id }?.email
        return userEmail
    }
}