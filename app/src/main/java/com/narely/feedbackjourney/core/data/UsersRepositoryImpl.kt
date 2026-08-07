package com.narely.feedbackjourney.core.data

import com.narely.feedbackjourney.core.services.ApiService
import javax.inject.Inject

interface UsersRepository {

}

class UsersRepositoryImpl @Inject constructor(private val apiService: ApiService): UsersRepository {


}