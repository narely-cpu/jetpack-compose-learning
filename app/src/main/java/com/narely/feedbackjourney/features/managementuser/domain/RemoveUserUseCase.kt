package com.narely.feedbackjourney.features.managementuser.domain

import com.google.gson.Gson
import com.narely.feedbackjourney.commons.data.remote.model.ErrorResponse
import com.narely.feedbackjourney.features.managementuser.data.ManagementUserRepository
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import retrofit2.HttpException
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(val managementUserRepository: ManagementUserRepository) {

    suspend fun invoke(
        collaborator: UserDataModel,
        deleteManagementUser: () -> Unit,
        errorMessage: (String?) -> Unit
    ) {
        try {
            managementUserRepository.removeUser(collaborator.id)
            deleteManagementUser()
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