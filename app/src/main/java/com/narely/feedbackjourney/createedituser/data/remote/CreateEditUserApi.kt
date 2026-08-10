package com.narely.feedbackjourney.createedituser.data.remote

import com.narely.feedbackjourney.BuildConfig
import com.narely.feedbackjourney.core.data.remote.model.UserResponse
import com.narely.feedbackjourney.core.data.remote.model.UsersListResponse
import com.narely.feedbackjourney.createedituser.data.remote.model.CreateEditUserRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CreateEditUserApi {
    @GET("users/{id}")
    @Headers("Authorization: Bearer ${BuildConfig.ADMIN_TOKEN}")
    suspend fun getUser(@Path("id") id: Int): UserResponse?

    @GET("users?type=PDM&size=100")
    @Headers("Authorization: Bearer ${BuildConfig.ADMIN_TOKEN}")
    suspend fun getListPdm(): UsersListResponse

    @POST("users")
    @Headers("Authorization: Bearer ${BuildConfig.ADMIN_TOKEN}")
    suspend fun createUser(@Body request: CreateEditUserRequest): UserResponse

    @PUT("users/{id}")
    @Headers("Authorization: Bearer ${BuildConfig.ADMIN_TOKEN}")
    suspend fun updateUser(@Path("id") id: Int, @Body request: CreateEditUserRequest): UserResponse
}