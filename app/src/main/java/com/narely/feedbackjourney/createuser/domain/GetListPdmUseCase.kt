package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import javax.inject.Inject

class GetListPdmUseCase @Inject constructor(val usersRepository: UsersRepository) {

    fun invoke(): List<String> {
        return usersRepository.getListPdm()
    }
}