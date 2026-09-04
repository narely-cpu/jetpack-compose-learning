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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.ui.theme.Blue80
import com.narely.feedbackjourney.ui.theme.Grey40
import com.narely.feedbackjourney.ui.theme.Grey80
import com.narely.feedbackjourney.ui.theme.Typography

@Composable
fun TextInputForm(
    label: String?,
    placeholder: String?,
    valueState: String,
    trailingIcon: @Composable (() -> Unit)?,
    modifier: Modifier,
    readOnly: Boolean = false,
    updateValueState: (String) -> Unit,
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        label?.let {
            Text(
                label,
                style = Typography.labelMedium,
                color = Blue80
            )
        }
        TextField(
            value = valueState,
            placeholder = { placeholder?.let { Text(placeholder) } },
            onValueChange = updateValueState,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Grey80,
                errorTextColor = Color.Red,
                focusedContainerColor = Grey40,
                unfocusedContainerColor = Grey40,
                focusedIndicatorColor = Grey40,
                unfocusedIndicatorColor = Grey40,
                focusedPlaceholderColor = Grey80,
                unfocusedPlaceholderColor = Grey80
            ),
            readOnly = readOnly,
            modifier = modifier,
            trailingIcon = trailingIcon
        )
    }
}

@Preview
@Composable
private fun TextInputFormPreview() {
    TextInputForm(
        label = "Email",
        placeholder = "Placeholder",
        valueState = "",
        trailingIcon = { },
        modifier = Modifier.fillMaxWidth()
            .height(50.dp)
    ) {}
}