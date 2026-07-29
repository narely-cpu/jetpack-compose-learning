package com.narely.feedbackjourney.core.model

import com.google.gson.annotations.SerializedName

data class UsersListResponse(
    // TODO: add default values
    @SerializedName("content") val content: List<UserResponse> = emptyList(),
    @SerializedName("pageable") val pageable: Any? = null,
    @SerializedName("last") val last: Boolean? = null,
    @SerializedName("totalElements") val totalElements: Int? = null,
    @SerializedName("totalPages") val totalPages: Int? = null,
    @SerializedName("size") val size: Int? = null,
    @SerializedName("number") val number: Int? = null,
    @SerializedName("sort") val sort: Any? = null,
    @SerializedName("first") val first: Boolean? = null,
    @SerializedName("numberOfElements") val numberOfElements: Int? = null,
    @SerializedName("empty") val empty: Boolean? = null,
)
