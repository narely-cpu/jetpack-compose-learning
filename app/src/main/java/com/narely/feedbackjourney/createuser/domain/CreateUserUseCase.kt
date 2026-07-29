package com.narely.feedbackjourney.createuser.domain

import com.google.gson.Gson
import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.CreateUserRequest
import com.narely.feedbackjourney.core.model.ErrorResponse
import retrofit2.HttpException
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(val usersRepository: UsersRepository) {

    suspend fun invoke(name: String,
                       email: String,
                       userType: String,
                       pdmEmail: String?,
                       finishedActivityCreateUser: () -> Unit,
                       errorMessage: (String?) -> Unit
    ) {
        val pdmList = usersRepository.getListPdm()
        val pdmId = pdmList.find { it.email == pdmEmail}?.id

        try {
            val request = CreateUserRequest(
                name = name,
                email = email,
                type = userType,
                pdmId = pdmId
            )

            usersRepository.createUser(request)
            finishedActivityCreateUser()
        } catch (e: HttpException) {
            val errorResponse = e.response()?.errorBody()?.string()
            errorResponse?.let {
                val error = Gson().fromJson(it, ErrorResponse::class.java)
                errorMessage(error.error)
            }
        }
    }
}