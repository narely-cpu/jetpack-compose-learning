package com.narely.feedbackjourney.features.login.ui

data class LoginViewState(
    val email: String = "",
    val password: String = "",
    val token: String? = "",
    val errorMessage: String? = "",
)