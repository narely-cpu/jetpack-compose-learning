package com.narely.feedbackjourney.home.login

import com.narely.feedbackjourney.core.data.UsersRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(val usersRepository: UsersRepository) {
    suspend fun invoke(request: LoginRequest): LoginResponse {
        return usersRepository.login(request)
    }
}