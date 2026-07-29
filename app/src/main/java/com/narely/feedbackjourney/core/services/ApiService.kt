package com.narely.feedbackjourney.core.services

import com.narely.feedbackjourney.core.model.CreateUserRequest
import com.narely.feedbackjourney.core.model.UserResponse
import com.narely.feedbackjourney.core.model.UsersListResponse
import com.narely.feedbackjourney.home.login.LoginRequest
import com.narely.feedbackjourney.home.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("users")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NTM2NDg3OCwiZXhwIjoxNzg1Mzc5Mjc4LCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.AbK3O_JFeymTaOZoI7QgmDIXIRuJJAiTUrZ_WbX6Cuk")
    suspend fun getUsers(): UsersListResponse

    @GET("users/{id}")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NTM2NDg3OCwiZXhwIjoxNzg1Mzc5Mjc4LCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.AbK3O_JFeymTaOZoI7QgmDIXIRuJJAiTUrZ_WbX6Cuk")
    suspend fun getUser(@Path("id") id: Int): UserResponse?

    @GET("users?type=PDM&size=100")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NTM2NDg3OCwiZXhwIjoxNzg1Mzc5Mjc4LCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.AbK3O_JFeymTaOZoI7QgmDIXIRuJJAiTUrZ_WbX6Cuk")
    suspend fun getListPdm(): UsersListResponse

    @POST("users/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("users")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NTM2NDg3OCwiZXhwIjoxNzg1Mzc5Mjc4LCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.AbK3O_JFeymTaOZoI7QgmDIXIRuJJAiTUrZ_WbX6Cuk")
    suspend fun createUser(@Body request: CreateUserRequest): UserResponse

    @DELETE("users/{id}")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NTM2NDg3OCwiZXhwIjoxNzg1Mzc5Mjc4LCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.AbK3O_JFeymTaOZoI7QgmDIXIRuJJAiTUrZ_WbX6Cuk")
    suspend fun removeUser(@Path("id") id: Int): Response<Unit>
}
