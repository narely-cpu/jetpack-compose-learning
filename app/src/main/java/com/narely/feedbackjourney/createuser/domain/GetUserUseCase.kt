package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserResponse
import javax.inject.Inject

class GetUserUseCase @Inject constructor(val usersRepository: UsersRepository) {

    suspend fun invoke(id: Int): UserResponse? {
       return usersRepository.getUser(id)
    }
}