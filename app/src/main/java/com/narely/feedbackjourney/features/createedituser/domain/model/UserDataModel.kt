package com.narely.feedbackjourney.features.createedituser.domain.model

data class UserDataModel(
    val id: Int = 0,
    var name: String = "",
    var email: String = "",
    var type: UserTypeEnum = UserTypeEnum.COLLABORATOR,
    var pdmEmail: String? = null
)