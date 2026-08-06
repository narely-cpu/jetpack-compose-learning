package com.narely.feedbackjourney.home.login

data class LoginViewState(
    val email: String = "Email",
    val password: String = "Password",
    val token: String? = "",
    val errorMessage: String? = "",
)