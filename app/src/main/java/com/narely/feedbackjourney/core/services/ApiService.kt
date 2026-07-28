package com.narely.feedbackjourney.core.services

import com.narely.feedbackjourney.core.model.UserResponse
import com.narely.feedbackjourney.core.model.UsersResponse
import com.narely.feedbackjourney.home.login.LoginRequest
import com.narely.feedbackjourney.home.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("users")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NTI1ODg3NiwiZXhwIjoxNzg1MjczMjc2LCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.ZAABebfHWlEdOf_ZyBSkPQbQdt8YX2VedNidkY2sQeM")
    suspend fun getUsers(): UsersResponse

    @POST("users/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("users/{id}")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NTI1ODg3NiwiZXhwIjoxNzg1MjczMjc2LCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.ZAABebfHWlEdOf_ZyBSkPQbQdt8YX2VedNidkY2sQeM")
    suspend fun getUser(@Path("id") id: Int): UserResponse?

    @DELETE("users/{id}")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NTI1ODg3NiwiZXhwIjoxNzg1MjczMjc2LCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.ZAABebfHWlEdOf_ZyBSkPQbQdt8YX2VedNidkY2sQeM")
    suspend fun deleteUser(@Path("id") id: Int)
}
