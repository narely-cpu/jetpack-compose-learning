package com.narely.feedbackjourney.login.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String
)
