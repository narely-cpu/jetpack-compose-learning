package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserDataModel

class GetUserUseCase(val usersRepository: UsersRepository) {

    fun invoke(userId: String?): UserDataModel? {
       return usersRepository.getUser(userId)
    }
}