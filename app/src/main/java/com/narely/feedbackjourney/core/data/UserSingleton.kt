package com.narely.feedbackjourney.core.data

import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType

internal object UserSingleton {
    val listUser: MutableList<UserDataModel> = mutableListOf(
        UserDataModel(
            "sdd",
            "narely",
            "narely@ciandt.com",
            "senha",
            UserType.PDM,
            null
        )
    )
}