package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.createuser.CreateEditUserViewState
import kotlinx.coroutines.flow.StateFlow

class EditUserUseCase(val usersRepository: UsersRepository) {

    fun invoke(value: CreateEditUserViewState) {
        usersRepository.updateUser(value)
    }
}