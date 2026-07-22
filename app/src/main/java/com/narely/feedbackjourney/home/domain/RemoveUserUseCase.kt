package com.narely.feedbackjourney.home.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(val usersRepository: UsersRepository) {

    fun invoke(id: String) {
         usersRepository.removeUser(id)
    }
}