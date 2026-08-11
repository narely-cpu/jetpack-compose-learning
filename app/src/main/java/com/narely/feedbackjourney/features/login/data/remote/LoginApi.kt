package com.narely.feedbackjourney.features.login.data.remote

import com.narely.feedbackjourney.features.login.data.remote.model.LoginRequest
import com.narely.feedbackjourney.features.login.data.remote.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("users/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}