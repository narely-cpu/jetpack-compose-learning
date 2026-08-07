package com.narely.feedbackjourney.home.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.core.data.remote.model.ErrorResponse
import com.narely.feedbackjourney.home.data.HomeRepository
import retrofit2.HttpException
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(val homeRepository: HomeRepository) {

    suspend fun invoke(id: Int) {
        try {
            homeRepository.removeUser(id)
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()

                errorResponse?.let {
                    val error = Gson().fromJson(it, ErrorResponse::class.java)
                    Log.e("error http remove user:", error.error)
                }
            } else {
                e.message?.let { Log.e("error remove user:", it) }
            }
        }
    }
}