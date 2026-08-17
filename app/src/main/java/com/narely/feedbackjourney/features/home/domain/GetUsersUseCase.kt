package com.narely.feedbackjourney.features.home.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.commons.data.remote.model.ErrorResponse
import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.home.data.HomeRepository
import retrofit2.HttpException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(val homeRepository: HomeRepository) {

    suspend fun invoke(): List<UserResponse> {
        try {
            return homeRepository.getUsers()
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