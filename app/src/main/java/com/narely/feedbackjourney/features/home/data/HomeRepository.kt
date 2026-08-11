package com.narely.feedbackjourney.features.home.data

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.home.data.remote.HomeApi
import javax.inject.Inject

interface HomeRepository {
    suspend fun getUsers(): List<UserResponse>
    suspend fun removeUser(id: Int)
}

class HomeRepositoryImpl @Inject constructor(private val homeApi: HomeApi): HomeRepository {

    override suspend fun getUsers(): List<UserResponse> {
        val getUsersResponse = homeApi.getUsers()
        val listUsers = getUsersResponse.listUsers.filter { it.active }

        return listUsers
    }

    override suspend fun removeUser(id: Int) {
        homeApi.removeUser(id)
    }
}