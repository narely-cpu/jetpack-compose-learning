package com.narely.feedbackjourney.commons.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.narely.feedbackjourney.R

@Composable
fun EnterpriseLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.frame_logo),
        contentDescription = stringResource(id = R.string.cit_logo),
        modifier = modifier
    )
}