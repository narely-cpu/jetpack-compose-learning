package com.narely.feedbackjourney.core.data

import com.narely.feedbackjourney.core.model.CreateUserRequest
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserResponse
import com.narely.feedbackjourney.core.model.UserType
import com.narely.feedbackjourney.core.services.ApiService
import com.narely.feedbackjourney.home.login.LoginRequest
import com.narely.feedbackjourney.home.login.LoginResponse
import javax.inject.Inject

interface UsersRepository {
    suspend fun getUsers(): List<UserResponse>
    suspend fun getUser(id: Int): UserResponse?
    suspend fun createUser(request: CreateUserRequest)
    suspend fun removeUser(id: Int)
    fun updateUser(id: Int, name: String, email: String, password: String, userType: UserType, pdmEmail: String?)
    suspend fun getListPdm(): List<UserResponse>

    suspend fun login(request: LoginRequest): LoginResponse
}

class UsersRepositoryImpl @Inject constructor(items: List<UserDataModel>? = null, private val apiService: ApiService): UsersRepository {

    val listUser = items?.toMutableList() ?: mutableListOf()

    override suspend fun getUsers(): List<UserResponse> {
        val getUsersResponse = apiService.getUsers()
        val listUsers = getUsersResponse.content.filter { it.active }

        return listUsers
    }

    override suspend fun getUser(id: Int): UserResponse? {
        return apiService.getUser(id)
    }

    override suspend fun createUser(request: CreateUserRequest) {
        apiService.createUser(request)
    }

    override suspend fun removeUser(id: Int) {
        apiService.removeUser(id)
    }

    override fun updateUser(id: Int,
                            name: String,
                            email: String,
                            password: String,
                            userType: UserType,
                            pdmEmail: String?
    ){
        val user = listUser.find { it.id == id }

        if (user != null) {
            val newUser = listUser[listUser.indexOf(user)]
                .copy(name = name,
                    email = email,
                    password = password,
                    userType = userType,
                    pdmEmail = pdmEmail
                )
            listUser[listUser.indexOf(user)] = newUser
        }
    }

    override suspend fun getListPdm(): List<UserResponse> {
        val getListPdmResponse = apiService.getListPdm()
        val listPdm = getListPdmResponse.content

        return listPdm
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.login(request)
    }
}