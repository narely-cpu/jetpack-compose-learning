package com.narely.feedbackjourney.features.managementuser.data

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.core.singleton.AuthSingleton
import com.narely.feedbackjourney.features.managementuser.data.remote.model.CreateEditUserRequest
import com.narely.feedbackjourney.features.managementuser.data.remote.ManagementUserApi
import javax.inject.Inject

interface ManagementUserRepository {
    suspend fun getUsers(): List<UserResponse>
    suspend fun getUser(id: Int): UserResponse?
    suspend fun createUser(request: CreateEditUserRequest)
    suspend fun updateUser(id: Int, request: CreateEditUserRequest)
    suspend fun getListPdm(): List<UserResponse>
    suspend fun removeUser(id: Int)
}

class ManagementUserRepositoryImpl @Inject constructor(private val managementUserApi: ManagementUserApi, private val token: String): ManagementUserRepository {

    override suspend fun getUsers(): List<UserResponse> {
        val getUsersResponse = managementUserApi.getUsers("Bearer $token")
        val listUsers = getUsersResponse.listUsers.filter { it.active }

        return listUsers
    }

    override suspend fun getUser(id: Int): UserResponse? {
        return managementUserApi.getUser(token, id)
    }

    override suspend fun createUser(request: CreateEditUserRequest) {
        managementUserApi.createUser(token, request)
    }

    override suspend fun updateUser(id: Int, request: CreateEditUserRequest) {
        managementUserApi.updateUser(token, id, request)
    }

    override suspend fun getListPdm(): List<UserResponse> {
        val getListPdmResponse = managementUserApi.getListPdm(token)
        val listPdm = getListPdmResponse.listUsers

        return listPdm
    }

    override suspend fun removeUser(id: Int) {
        managementUserApi.removeUser(token, id)
    }
}