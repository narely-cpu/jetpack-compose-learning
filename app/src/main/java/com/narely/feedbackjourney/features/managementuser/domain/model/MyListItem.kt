package com.narely.feedbackjourney.features.managementuser.domain.model


data class MyListItem(val onClick: () -> Unit, val painterId: Int, val contentDescription: Int)
