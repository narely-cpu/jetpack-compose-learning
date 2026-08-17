package com.narely.feedbackjourney.features.createedituser.data

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.createedituser.data.remote.CreateEditUserApi
import com.narely.feedbackjourney.features.createedituser.data.remote.model.CreateEditUserRequest
import javax.inject.Inject

interface CreateEditUserRepository {
    suspend fun getUser(id: Int): UserResponse?
    suspend fun createUser(request: CreateEditUserRequest)
    suspend fun updateUser(id: Int, request: CreateEditUserRequest)
    suspend fun getListPdm(): List<UserResponse>
}

class CreateEditUserRepositoryImpl @Inject constructor(private val createEditUserApi: CreateEditUserApi): CreateEditUserRepository {

    override suspend fun getUser(id: Int): UserResponse? {
        return createEditUserApi.getUser(id)
    }

    override suspend fun createUser(request: CreateEditUserRequest) {
        createEditUserApi.createUser(request)
    }

    override suspend fun updateUser(id: Int, request: CreateEditUserRequest) {
        createEditUserApi.updateUser(id, request)
    }

    override suspend fun getListPdm(): List<UserResponse> {
        val getListPdmResponse = createEditUserApi.getListPdm()
        val listPdm = getListPdmResponse.listUsers

        return listPdm
    }
}