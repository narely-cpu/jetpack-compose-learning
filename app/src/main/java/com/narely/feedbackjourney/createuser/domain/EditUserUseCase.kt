package com.narely.feedbackjourney.createuser.domain

import android.util.Log
import com.google.gson.Gson
import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.ErrorResponse
import com.narely.feedbackjourney.core.model.UpdateUserRequest
import retrofit2.HttpException
import javax.inject.Inject

class EditUserUseCase @Inject constructor(val usersRepository: UsersRepository) {

    suspend fun invoke(
        id: Int,
        name: String,
        email: String,
        userType: String,
        pdmEmail: String?,
        finishedActivityCreateUser: () -> Unit,
        errorMessage: (String?) -> Unit
    ) {
        try {
            val pdmList = usersRepository.getListPdm()
            val pdmId = pdmList.find { it.email == pdmEmail}?.id
            val request = UpdateUserRequest(
                name = name,
                email = email,
                type = userType,
                pdmId = pdmId
            )

            usersRepository.updateUser(id = id, request = request)
            finishedActivityCreateUser()
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()

                errorResponse?.let {
                    val error = Gson().fromJson(it, ErrorResponse::class.java)
                    errorMessage(error.error)
                }
            } else {
                errorMessage(e.message)
            }
        }
    }
}