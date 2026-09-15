package com.narely.feedbackjourney.features.managementuser.domain.mapper

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.managementuser.domain.model.UserTypeEnum

fun UserResponse.toDomain(pdmEmail: String?): UserDataModel {
    return UserDataModel(
        id = this.id,
        name = this.name,
        email = this.email,
        type = this.type.toUserType(),
        pdmEmail = pdmEmail
    )
}

fun String.toUserType(): UserTypeEnum {
    return when (this) {
        "ADMIN" -> UserTypeEnum.ADMIN
        "COLLABORATOR" -> UserTypeEnum.COLLABORATOR
        "PDM" -> UserTypeEnum.PDM
        else -> UserTypeEnum.COLLABORATOR
    }
}