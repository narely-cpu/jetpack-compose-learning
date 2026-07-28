package com.narely.feedbackjourney.core.model

data class UserDataModel(val id: Int,
                         val name: String,
                         val email: String,
                         val password: String,
                         val userType: UserType,
                         val pdmEmail: String?)