package com.narely.feedbackjourney.features.managementuser.domain.model

data class UserDataModel(
    val id: Int = 0,
    var name: String = "",
    var email: String = "",
    var type: UserTypeEnum? = null,
    var pdmEmail: String? = null
)