package com.narely.feedbackjourney.features.createedituser.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.commons.data.remote.model.ErrorResponse
import com.narely.feedbackjourney.features.createedituser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.createedituser.data.CreateEditUserRepository
import com.narely.feedbackjourney.features.createedituser.domain.mapper.toDomain
import retrofit2.HttpException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(val createEditUserRepository: CreateEditUserRepository) {

    suspend fun invoke(id: Int, listPdm: List<UserDataModel>?): UserDataModel? {

        try {
            val userResponse = createEditUserRepository.getUser(id)
            val pdm = listPdm?.find { it.id == userResponse?.pdmId }
            val user = userResponse?.toDomain(pdmEmail = pdm?.email)

            return user
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()

                errorResponse?.let {
                    val error = Gson().fromJson(it, ErrorResponse::class.java)
                    Log.e("error get user:", error.error)
                }
            } else {
                e.message?.let { Log.e("error get user:", it) }
            }
        }

        return null
    }
}