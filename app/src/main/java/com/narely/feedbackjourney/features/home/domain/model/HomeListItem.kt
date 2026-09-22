package com.narely.feedbackjourney.features.home.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class HomeListItem(
    val onClick: () -> Unit,
    @DrawableRes val painterId: Int,
    @StringRes val contentDescription: Int
)
