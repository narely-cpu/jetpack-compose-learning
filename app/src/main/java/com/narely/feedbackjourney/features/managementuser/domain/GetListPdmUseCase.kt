package com.narely.feedbackjourney.features.managementuser.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.commons.data.remote.model.ErrorResponse
import com.narely.feedbackjourney.features.managementuser.data.ManagementUserRepository
import com.narely.feedbackjourney.features.managementuser.domain.mapper.toDomain
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import retrofit2.HttpException
import javax.inject.Inject

class GetListPdmUseCase @Inject constructor(val managementUserRepository: ManagementUserRepository) {

    suspend fun invoke(): List<UserDataModel>? {
        try {
            val getListPdm = managementUserRepository.getListPdm()
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