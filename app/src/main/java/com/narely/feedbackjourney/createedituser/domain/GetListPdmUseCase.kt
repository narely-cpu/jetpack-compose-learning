package com.narely.feedbackjourney.createedituser.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.core.model.ErrorResponse
import com.narely.feedbackjourney.createedituser.data.CreateEditUserRepository
import retrofit2.HttpException
import javax.inject.Inject

class GetListPdmUseCase @Inject constructor(val createEditUserRepository: CreateEditUserRepository) {

    suspend fun invoke(): List<String>? {
        try {
            val getListPdm = createEditUserRepository.getListPdm()
            val listPdmEmail: MutableList<String> = mutableListOf()

            getListPdm.forEach { listPdmEmail.add(it.email) }

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