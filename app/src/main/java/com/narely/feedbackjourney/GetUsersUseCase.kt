package com.narely.feedbackjourney

import com.narely.feedbackjourney.createuser.UserDataModel

class GetUsersUseCase(val usersRepository: UsersRepository) {

    fun invoke(): List<UserDataModel> {
        return usersRepository.getUsers()
    }
}