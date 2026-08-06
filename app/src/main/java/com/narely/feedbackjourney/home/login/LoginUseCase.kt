package com.narely.feedbackjourney.home.login

import com.google.gson.Gson
import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.ErrorResponse
import retrofit2.HttpException
import javax.inject.Inject

class LoginUseCase @Inject constructor(val usersRepository: UsersRepository) {

    suspend fun invoke(
        email: String,
        password: String,
        tokenResponse: (String?) -> Unit,
        errorMessage: (String?) -> Unit
    ): LoginResponse {
        val request = LoginRequest(email = email, password = password)

        try {
            val loginResponse = usersRepository.login(request)

            tokenResponse(loginResponse.token)
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()

                errorResponse?.let {
                    val error = Gson().fromJson(it, ErrorResponse::class.java)
                    errorMessage(error.error)
                }
            } else {
                errorMessage(e.message)
            }
        }

        return usersRepository.login(request)
    }
}