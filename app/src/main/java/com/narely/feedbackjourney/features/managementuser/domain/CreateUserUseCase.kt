package com.narely.feedbackjourney.features.managementuser.domain

import com.google.gson.Gson
import com.narely.feedbackjourney.commons.data.remote.model.ErrorResponse
import com.narely.feedbackjourney.features.managementuser.data.ManagementUserRepository
import com.narely.feedbackjourney.features.managementuser.data.remote.model.CreateEditUserRequest
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.managementuser.domain.model.UserTypeEnum
import retrofit2.HttpException
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(val managementUserRepository: ManagementUserRepository) {

    suspend fun invoke(
        collaborator: UserDataModel,
        pdm: UserDataModel?,
        updateManagementUser: () -> Unit,
        errorMessage: (String?) -> Unit
    ) {
        try {
            val request = CreateEditUserRequest(
                name = collaborator.name,
                email = collaborator.email,
                type = collaborator.type?.name ?: UserTypeEnum.COLLABORATOR.name,
                pdmId = pdm?.id
            )

            managementUserRepository.createUser(request)
            updateManagementUser()
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