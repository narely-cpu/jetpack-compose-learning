package com.narely.feedbackjourney

import com.narely.feedbackjourney.createuser.UserDataModel

class UsersRepository() {

    val listUser: MutableList<UserDataModel> = mutableListOf()

    fun getUsers(): List<UserDataModel> {
        return listUser
    }

    fun saveUser(userModel: UserDataModel) {}

    fun removeUser(userModel: UserDataModel) {}

    fun updateUser(userModel: UserDataModel) {}
}