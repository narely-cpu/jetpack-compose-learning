package com.narely.feedbackjourney.features.login.ui

data class LoginViewState(
    val email: String = "Email",
    val password: String = "Password",
    val token: String? = "",
    val errorMessage: String? = "",
)