package com.narely.feedbackjourney.createuser.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.ErrorResponse
import retrofit2.HttpException
import javax.inject.Inject

class GetListPdmUseCase @Inject constructor(val usersRepository: UsersRepository) {

    suspend fun invoke(): List<String>? {
        try {
            val getListPdm = usersRepository.getListPdm()
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
            }

            e.message?.let { Log.e("error get list pdm:", it) }
        }

        return null
    }
}