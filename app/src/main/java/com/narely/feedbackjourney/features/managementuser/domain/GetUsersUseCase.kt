package com.narely.feedbackjourney.features.managementuser.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.commons.data.remote.model.ErrorResponse
import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.managementuser.data.ManagementUserRepository
import retrofit2.HttpException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(val managementUserRepository: ManagementUserRepository) {

    suspend fun invoke(): List<UserResponse> {
        try {
            return managementUserRepository.getUsers()
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