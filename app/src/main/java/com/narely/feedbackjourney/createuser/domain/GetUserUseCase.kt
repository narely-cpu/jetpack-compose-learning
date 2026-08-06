package com.narely.feedbackjourney.createuser.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.ErrorResponse
import com.narely.feedbackjourney.core.model.UserDataModel
import retrofit2.HttpException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(val usersRepository: UsersRepository) {

    suspend fun invoke(id: Int): UserDataModel? {
        val password = "Collaborator123!"

        try {
            val pdmList = usersRepository.getListPdm()
            val userResponse = usersRepository.getUser(id)
            val pdmEmail = pdmList.find { it.id == userResponse?.pdmId }?.email
            if (userResponse != null) {
                val user = UserDataModel(
                    id = id,
                    name = userResponse.name,
                    email = userResponse.email,
                    password = password,
                    type = userResponse.type,
                    pdmEmail = pdmEmail
                )

                return user
            }
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