package com.narely.feedbackjourney.features.managementuser.data.remote

import com.narely.feedbackjourney.BuildConfig
import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.commons.data.remote.model.UsersListResponse
import com.narely.feedbackjourney.features.managementuser.data.remote.model.CreateEditUserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ManagementUserApi {

    @GET("users")
    suspend fun getUsers(
        @Header("Authorization") token: String?
    ): UsersListResponse

    @GET("users/{id}")
    suspend fun getUser(
        @Header("Authorization") token: String?,
        @Path("id") id: Int
    ): UserResponse?

    @GET("users?type=PDM&size=100")
    suspend fun getListPdm(
        @Header("Authorization") token: String?
    ): UsersListResponse

    @POST("users")
    suspend fun createUser(
        @Header("Authorization") token: String?,
        @Body request: CreateEditUserRequest
    ): UserResponse

    @PUT("users/{id}")
    suspend fun updateUser(
        @Header("Authorization") token: String?,
        @Path("id") id: Int,
        @Body request: CreateEditUserRequest
    ): UserResponse

    @DELETE("users/{id}")
    suspend fun removeUser(
        @Header("Authorization") token: String?,
        @Path("id") id: Int
    ): Call<Unit>
}