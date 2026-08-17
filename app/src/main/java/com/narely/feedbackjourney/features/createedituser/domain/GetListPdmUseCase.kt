package com.narely.feedbackjourney.features.createedituser.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.commons.data.remote.model.ErrorResponse
import com.narely.feedbackjourney.features.createedituser.data.CreateEditUserRepository
import com.narely.feedbackjourney.features.createedituser.domain.mapper.toDomain
import com.narely.feedbackjourney.features.createedituser.domain.model.UserDataModel
import retrofit2.HttpException
import javax.inject.Inject

class GetListPdmUseCase @Inject constructor(val createEditUserRepository: CreateEditUserRepository) {

    suspend fun invoke(): List<UserDataModel>? {
        try {
            val getListPdm = createEditUserRepository.getListPdm()
            val listPdmEmail = getListPdm.map { it.toDomain(it.email) }

            return listPdmEmail
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()

                errorResponse?.let {
                    val error = Gson().fromJson(it, ErrorResponse::class.java)
                    Log.e("error http get list pdm:", error.error)
                }
            } else {
                e.message?.let { Log.e("error get list pdm:", it) }
            }
        }

        return null
    }
}