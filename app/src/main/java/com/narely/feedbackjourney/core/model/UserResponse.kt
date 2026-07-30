package com.narely.feedbackjourney.core.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("type") var type: String,
    @SerializedName("pdmId") var pdmId: Int,
    @SerializedName("active") var active: Boolean
)
