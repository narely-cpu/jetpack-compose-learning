package com.narely.feedbackjourney.home.data.remote

import com.narely.feedbackjourney.core.data.remote.model.UsersListResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

private const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1c2VyLWFwaSIsImlhdCI6MTc4NjM4NTA4MSwiZXhwIjoxNzg2Mzk5NDgxLCJzdWIiOiJhZG1pbkBjaWFuZHQuY29tIiwidXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcIkFkbWluXCIsXCJlbWFpbFwiOlwiYWRtaW5AY2lhbmR0LmNvbVwiLFwidHlwZVwiOlwiQURNSU5cIixcInBkbVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.svgaVka8rKQ8bL2jZk-lCZ9KXB_qj_A6tdbFAQ6Ba14"

interface HomeApi {

    @GET("users")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getUsers(): UsersListResponse

    @DELETE("users/{id}")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun removeUser(@Path("id") id: Int): Response<Unit>

}