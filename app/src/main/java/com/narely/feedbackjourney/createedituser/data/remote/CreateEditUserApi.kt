package com.narely.feedbackjourney.createedituser.data.remote

import com.narely.feedbackjourney.core.data.remote.model.UserResponse
import com.narely.feedbackjourney.core.data.remote.model.UsersListResponse
import com.narely.feedbackjourney.createedituser.data.remote.model.CreateEditUserRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NjM4NTA4MSwiZXhwIjoxNzg2Mzk5NDgxLCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.svgaVka8rKQ8bL2jZk-lCZ9KXB_qj_A6tdbFAQ6Ba14"

interface CreateEditUserApi {
    @GET("users/{id}")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getUser(@Path("id") id: Int): UserResponse?

    @GET("users?type=PDM&size=100")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getListPdm(): UsersListResponse

    @POST("users")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun createUser(@Body request: CreateEditUserRequest): UserResponse

    @PUT("users/{id}")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun updateUser(@Path("id") id: Int, @Body request: CreateEditUserRequest): UserResponse
}