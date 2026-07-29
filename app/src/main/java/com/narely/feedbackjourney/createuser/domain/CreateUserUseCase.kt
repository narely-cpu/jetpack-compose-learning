package com.narely.feedbackjourney.createuser.domain

import android.util.Log
import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.CreateUserRequest
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(val usersRepository: UsersRepository) {

    suspend fun invoke(name: String, email: String, password: String, userType: String, pdmEmail: String?, finishedActivityCreateUser: () -> Unit) {
        val pdmList = usersRepository.getListPdm()
        val pdmId = pdmList.find { it.email == pdmEmail}?.id

        try {
            val request = CreateUserRequest(
                name = name,
                email = email,
                password = password,
                type = userType,
                pdmId = pdmId
            )
            usersRepository.createUser(request)
        } catch (e: Exception) {
            e.message?.let { Log.e("Error create user: ", it) }
        } finally {
            finishedActivityCreateUser()
        }
    }
}