package com.narely.feedbackjourney.core.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("content") val content: List<UserResponse>,
    @SerializedName("pageable") val pageable: Any,
    @SerializedName("last") val last: Boolean,
    @SerializedName("totalElements") val totalElements: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("sort") val sort: Any,
    @SerializedName("first") val first: Boolean,
    @SerializedName("numberOfElements") val numberOfElements: Int,
    @SerializedName("empty") val empty: Boolean,
)
