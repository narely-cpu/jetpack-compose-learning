package com.narely.feedbackjourney.login.domain

import com.google.gson.Gson
import com.narely.feedbackjourney.core.model.ErrorResponse
import com.narely.feedbackjourney.login.data.LoginRepository
import com.narely.feedbackjourney.login.data.remote.model.LoginRequest
import com.narely.feedbackjourney.login.data.remote.model.LoginResponse
import retrofit2.HttpException
import javax.inject.Inject

class LoginUseCase @Inject constructor(val loginRepository: LoginRepository) {

    suspend fun invoke(
        email: String,
        password: String,
        tokenResponse: (String?) -> Unit,
        errorMessage: (String?) -> Unit
    ): LoginResponse {
        val request = LoginRequest(email = email, password = password)

        try {
            val loginResponse = loginRepository.login(request)

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

        return loginRepository.login(request)
    }
}