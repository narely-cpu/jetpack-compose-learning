package com.narely.feedbackjourney.commons.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.ui.theme.Blue80
import com.narely.feedbackjourney.ui.theme.Grey40
import com.narely.feedbackjourney.ui.theme.Typography

@Composable
fun TextInputForm(
    labelId: Int,
    valueState: String,
    updateValueState: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            stringResource(labelId),
            style = Typography.labelMedium,
            color = Blue80
        )
        TextField(
            value = valueState,
            onValueChange = updateValueState,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                errorTextColor = Color.Red,
                focusedContainerColor = Grey40,
                unfocusedContainerColor = Grey40,
                focusedIndicatorColor = Grey40,
                unfocusedIndicatorColor = Grey40
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }
}