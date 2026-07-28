package com.narely.feedbackjourney.core.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserResponse
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(val usersRepository: UsersRepository) {

    suspend fun invoke(): List<UserResponse> {
        return usersRepository.getUsers()
    }
}