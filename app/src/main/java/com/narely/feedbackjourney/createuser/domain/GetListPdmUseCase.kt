package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import javax.inject.Inject

class GetListPdmUseCase @Inject constructor(val usersRepository: UsersRepository) {

    suspend fun invoke(): List<String> {
        val getListPdm = usersRepository.getListPdm()
        val listPdmEmail: MutableList<String> = mutableListOf()

        getListPdm.forEach { listPdmEmail.add(it.email) }

        return listPdmEmail
    }
}