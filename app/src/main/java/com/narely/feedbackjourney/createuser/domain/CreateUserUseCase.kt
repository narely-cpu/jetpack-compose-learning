package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.createuser.CreateEditUserViewState

class CreateUserUseCase(val usersRepository: UsersRepository) {

    fun invoke(value: CreateEditUserViewState) {
        usersRepository.createUser(value)
    }
}