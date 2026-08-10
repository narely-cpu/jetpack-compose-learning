package com.narely.feedbackjourney.createedituser.domain

import com.google.gson.Gson
import com.narely.feedbackjourney.core.data.remote.model.ErrorResponse
import com.narely.feedbackjourney.createedituser.data.CreateEditUserRepository
import com.narely.feedbackjourney.createedituser.data.remote.model.CreateEditUserRequest
import retrofit2.HttpException
import javax.inject.Inject

class EditUserUseCase @Inject constructor(val createEditUserRepository: CreateEditUserRepository) {

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
            val pdmList = createEditUserRepository.getListPdm()
            val pdmId = pdmList.find { it.email == pdmEmail}?.id
            val request = CreateEditUserRequest(
                name = name,
                email = email,
                type = userType,
                pdmId = pdmId
            )

            createEditUserRepository.updateUser(id = id, request = request)
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