package com.narely.feedbackjourney.createedituser.data

import com.narely.feedbackjourney.createedituser.data.remote.model.CreateUserRequest
import com.narely.feedbackjourney.createedituser.data.remote.model.UpdateUserRequest
import com.narely.feedbackjourney.core.model.UserResponse
import com.narely.feedbackjourney.createedituser.data.remote.CreateEditUserApi
import javax.inject.Inject

interface CreateEditUserRepository {
    suspend fun getUser(id: Int): UserResponse?
    suspend fun createUser(request: CreateUserRequest)
    suspend fun updateUser(id: Int, request: UpdateUserRequest)
    suspend fun getListPdm(): List<UserResponse>
}

class CreateEditUserRepositoryImpl @Inject constructor(private val createEditUserApi: CreateEditUserApi): CreateEditUserRepository {

    override suspend fun getUser(id: Int): UserResponse? {
        return createEditUserApi.getUser(id)
    }

    override suspend fun createUser(request: CreateUserRequest) {
        createEditUserApi.createUser(request)
    }

    override suspend fun updateUser(id: Int, request: UpdateUserRequest) {
        createEditUserApi.updateUser(id, request)
    }

    override suspend fun getListPdm(): List<UserResponse> {
        val getListPdmResponse = createEditUserApi.getListPdm()
        val listPdm = getListPdmResponse.content

        return listPdm
    }
}