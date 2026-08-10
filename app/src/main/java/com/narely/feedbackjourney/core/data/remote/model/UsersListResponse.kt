package com.narely.feedbackjourney.core.data.remote.model

import com.google.gson.annotations.SerializedName

data class UsersListResponse(
    @SerializedName("content") val listUsers: List<UserResponse> = emptyList(),
)
