package com.narely.feedbackjourney.core.data

import com.narely.feedbackjourney.core.model.CreateUserRequest
import com.narely.feedbackjourney.core.model.UpdateUserRequest
import com.narely.feedbackjourney.core.model.UserResponse
import com.narely.feedbackjourney.core.services.ApiService
import javax.inject.Inject

interface UsersRepository {
    suspend fun getUser(id: Int): UserResponse?
    suspend fun createUser(request: CreateUserRequest)
    suspend fun updateUser(id: Int, request: UpdateUserRequest)
    suspend fun getListPdm(): List<UserResponse>
}

class UsersRepositoryImpl @Inject constructor(private val apiService: ApiService): UsersRepository {

    override suspend fun getUser(id: Int): UserResponse? {
        return apiService.getUser(id)
    }

    override suspend fun createUser(request: CreateUserRequest) {
        apiService.createUser(request)
    }

    override suspend fun updateUser(id: Int, request: UpdateUserRequest) {
        apiService.updateUser(id, request)
    }

    override suspend fun getListPdm(): List<UserResponse> {
        val getListPdmResponse = apiService.getListPdm()
        val listPdm = getListPdmResponse.content

        return listPdm
    }
}