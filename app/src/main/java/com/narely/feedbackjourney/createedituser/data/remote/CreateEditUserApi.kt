package com.narely.feedbackjourney.createedituser.data.remote

import com.narely.feedbackjourney.createedituser.data.remote.model.CreateUserRequest
import com.narely.feedbackjourney.createedituser.data.remote.model.UpdateUserRequest
import com.narely.feedbackjourney.core.model.UserResponse
import com.narely.feedbackjourney.core.model.UsersListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NjEzNDc0OCwiZXhwIjoxNzg2MTQ5MTQ4LCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.JXvTTpf78HubFsOBcV6qxvPLjuPNLcsJlPrq6QFmlMQ"

interface CreateEditUserApi {
    @GET("users/{id}")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getUser(@Path("id") id: Int): UserResponse?

    @GET("users?type=PDM&size=100")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getListPdm(): UsersListResponse

    @POST("users")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun createUser(@Body request: CreateUserRequest): UserResponse

    @PUT("users/{id}")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun updateUser(@Path("id") id: Int, @Body request: UpdateUserRequest): UserResponse
}