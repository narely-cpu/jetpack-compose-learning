package com.narely.feedbackjourney.features.createedituser.domain

import com.google.gson.Gson
import com.narely.feedbackjourney.commons.data.remote.model.ErrorResponse
import com.narely.feedbackjourney.features.createedituser.data.CreateEditUserRepository
import com.narely.feedbackjourney.features.createedituser.data.remote.model.CreateEditUserRequest
import com.narely.feedbackjourney.features.createedituser.domain.model.UserDataModel
import retrofit2.HttpException
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(val createEditUserRepository: CreateEditUserRepository) {

    suspend fun invoke(
        collaborator: UserDataModel,
        pdm: UserDataModel?,
        finishedActivityCreateUser: () -> Unit,
        errorMessage: (String?) -> Unit
    ) {
        try {
            val request = CreateEditUserRequest(
                name = collaborator.name,
                email = collaborator.email,
                type = collaborator.type.name,
                pdmId = pdm?.id
            )

            createEditUserRepository.createUser(request)
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