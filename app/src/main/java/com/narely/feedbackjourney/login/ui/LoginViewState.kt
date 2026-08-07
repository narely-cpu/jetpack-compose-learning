package com.narely.feedbackjourney.login.ui

data class LoginViewState(
    val email: String = "Email",
    val password: String = "Password",
    val token: String? = "",
    val errorMessage: String? = "",
)