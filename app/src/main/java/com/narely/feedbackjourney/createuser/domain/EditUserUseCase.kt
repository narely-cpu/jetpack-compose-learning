package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserType
import javax.inject.Inject

class EditUserUseCase @Inject constructor(val usersRepository: UsersRepository) {

    fun invoke(id: String, name: String, email: String, password: String, userType: String, pdmEmail: String?) {
        val userTypeUpdate = enumValueOf<UserType>(userType)
        usersRepository.updateUser(
            id = id,
            name = name,
            email = email,
            password = password,
            userType = userTypeUpdate,
            pdmEmail = pdmEmail)
    }
}