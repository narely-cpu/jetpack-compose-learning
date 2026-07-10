package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import com.narely.feedbackjourney.createuser.CreateEditUserViewState
import java.util.UUID
import kotlin.uuid.Uuid

class CreateUserUseCase(val usersRepository: UsersRepository) {

    fun invoke(name: String, email: String, password: String, userType: UserType, pdmEmail: String) {
        val id = UUID.randomUUID().toString()
        val userModel = UserDataModel(
            id = id,
            name = name,
            email = email,
            password = password,
            userType = userType,
            pdmEmail = pdmEmail
        )
        usersRepository.createUser(userModel)
    }
}