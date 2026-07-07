package com.narely.feedbackjourney.core.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserDataModel

class GetUsersUseCase(val usersRepository: UsersRepository) {

    fun invoke(): List<UserDataModel> {
        return usersRepository.getUsers()
    }
}