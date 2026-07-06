package com.narely.feedbackjourney.createuser

data class UserDataModel(val id: String,
                         val name: String,
                         val email: String,
                         val password: String,
                         val userType: UserType,
                         val pdmEmail: String?)