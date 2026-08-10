package com.narely.feedbackjourney.createedituser.data.remote.model

data class CreateEditUserRequest(
    val name: String,
    val email: String,
    val type: String,
    val pdmId: Int?
)