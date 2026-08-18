package com.narely.feedbackjourney.login.data

import com.narely.feedbackjourney.login.data.remote.LoginApi
import com.narely.feedbackjourney.login.data.remote.model.LoginRequest
import com.narely.feedbackjourney.login.data.remote.model.LoginResponse
import javax.inject.Inject

interface LoginRepository {
    suspend fun login(request: LoginRequest): LoginResponse
}

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi): LoginRepository {

    override suspend fun login(request: LoginRequest): LoginResponse {
        return loginApi.login(request)
    }
}