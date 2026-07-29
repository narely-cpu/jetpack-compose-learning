package com.narely.feedbackjourney.home.login

import com.narely.feedbackjourney.core.data.UsersRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(val usersRepository: UsersRepository) {
    suspend fun invoke(email: String,
                       password: String,
                       tokenResponse: (String?) -> Unit,
                       errorMessage: (String?) -> Unit
    ): LoginResponse {
        val request = LoginRequest(email = email, password = password)

        try {
            val loginResponse = usersRepository.login(request)
            tokenResponse(loginResponse.token)
        } catch (e: Exception) {
            errorMessage(e.message)
        }

        return usersRepository.login(request)
    }
}