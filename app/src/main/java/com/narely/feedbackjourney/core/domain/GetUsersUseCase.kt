package com.narely.feedbackjourney.core.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.ErrorResponse
import com.narely.feedbackjourney.core.model.UserResponse
import retrofit2.HttpException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(val usersRepository: UsersRepository) {

    suspend fun invoke(): List<UserResponse> {
        try {
            return usersRepository.getUsers()
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()

                errorResponse?.let {
                    val error = Gson().fromJson(it, ErrorResponse::class.java)
                    Log.e("error get users:", error.error)
                }
            } else {
                e.message?.let { Log.e("error get users:", it) }
            }
        }

        return emptyList()
    }
}