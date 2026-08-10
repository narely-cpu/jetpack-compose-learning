package com.narely.feedbackjourney.core.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("type") val type: String,
    @SerializedName("pdmId") val pdmId: Int?,
    @SerializedName("active") val active: Boolean
)
