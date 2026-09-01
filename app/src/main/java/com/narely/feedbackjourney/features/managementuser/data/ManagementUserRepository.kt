package com.narely.feedbackjourney.features.managementuser.data

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.managementuser.data.remote.ManagementUserApi
import javax.inject.Inject

interface ManagementUserRepository {
    suspend fun getUsers(): List<UserResponse>
    suspend fun removeUser(id: Int)
}

class ManagementUserRepositoryImpl @Inject constructor(private val managementUserApi: ManagementUserApi): ManagementUserRepository {

    override suspend fun getUsers(): List<UserResponse> {
        val getUsersResponse = managementUserApi.getUsers()
        val listUsers = getUsersResponse.listUsers.filter { it.active }

        return listUsers
    }

    override suspend fun removeUser(id: Int) {
        managementUserApi.removeUser(id)
    }
}