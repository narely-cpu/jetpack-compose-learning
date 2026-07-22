package com.narely.feedbackjourney.core.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import com.narely.feedbackjourney.core.model.UserDataModel
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(val usersRepository: UsersRepository) {

    fun invoke(): MutableList<UserDataModel> {
        return usersRepository.getUsers()
    }
}