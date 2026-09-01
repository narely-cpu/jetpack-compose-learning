package com.narely.feedbackjourney.features.managementuser.data.remote

import com.narely.feedbackjourney.BuildConfig
import com.narely.feedbackjourney.commons.data.remote.model.UsersListResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ManagementUserApi {

    @GET("users")
    @Headers("Authorization: Bearer ${BuildConfig.ADMIN_TOKEN}")
    suspend fun getUsers(): UsersListResponse

    @DELETE("users/{id}")
    @Headers("Authorization: Bearer ${BuildConfig.ADMIN_TOKEN}")
    suspend fun removeUser(@Path("id") id: Int): Response<Unit>

}