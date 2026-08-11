package com.narely.feedbackjourney.features.createedituser.domain.model

data class UserDataModel(
    val id: Int = 0,
    var name: String = "Name",
    var email: String = "Email",
    var type: UserTypeEnum = UserTypeEnum.COLLABORATOR,
    var pdmEmail: String? = null
)