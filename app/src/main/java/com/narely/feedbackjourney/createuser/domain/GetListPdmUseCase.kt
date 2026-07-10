package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository

class GetListPdmUseCase(val usersRepository: UsersRepository) {

    fun invoke(): List<String> {
        return usersRepository.getListPdm()
    }
}