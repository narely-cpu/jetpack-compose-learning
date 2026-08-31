package com.narely.feedbackjourney.features.home.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MyListItem(val onClick: () -> Unit, val painterId: Int, val contentDescription: Int)
