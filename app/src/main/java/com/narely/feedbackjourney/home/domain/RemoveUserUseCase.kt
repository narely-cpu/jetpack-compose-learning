package com.narely.feedbackjourney.home.domain

import com.narely.feedbackjourney.core.data.UsersRepository

class RemoveUserUseCase(val usersRepository: UsersRepository) {

    fun invoke(id: String) {
         usersRepository.removeUser(id)
    }
}