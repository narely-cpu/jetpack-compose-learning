package com.narely.feedbackjourney.core.model

data class UpdateUserRequest(
    val name: String,
    val email: String,
    val type: String,
    val pdmId: Int?
)
