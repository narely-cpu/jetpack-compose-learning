package com.narely.feedbackjourney.createedituser.domain.model

data class UserDataModel(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val type: String,
    val pdmEmail: String?
)