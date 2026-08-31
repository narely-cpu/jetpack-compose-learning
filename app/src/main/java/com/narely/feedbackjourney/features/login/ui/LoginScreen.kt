package com.narely.feedbackjourney.features.login.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.R.string
import com.narely.feedbackjourney.commons.ui.EnterpriseLogo
import com.narely.feedbackjourney.features.home.HomeActivity
import com.narely.feedbackjourney.ui.theme.Blue80
import com.narely.feedbackjourney.ui.theme.Grey40
import com.narely.feedbackjourney.ui.theme.Magenta80
import com.narely.feedbackjourney.ui.theme.Typography

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val formsUiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EnterpriseLogo(modifier = Modifier.padding(bottom = 64.dp))
        FormLoginLayout(
            userEmail = formsUiState.email,
            userPassword = formsUiState.password,
            onUserEmailChange = { viewModel.updateUiEmail(newEmail = it) },
            onUserPasswordChange = { viewModel.updateUiPassword(newPassword = it) }
        )
        LoginButton {
            viewModel.login()
            context.startActivity(
                Intent(
                    context,
                    HomeActivity::class.java
                )
            )
        }
    }
}

@Composable
private fun FormLoginLayout(
    userEmail: String,
    userPassword: String,
    onUserEmailChange: (String) -> Unit,
    onUserPasswordChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        TextInputForm(
            labelId = string.email_label,
            valueState = userEmail,
            updateValueState = onUserEmailChange
        )
        TextInputForm(
            labelId = string.password_label,
            valueState = userPassword,
            updateValueState = onUserPasswordChange
        )
        ForgetPasswordButton {  }
    }
}

@Composable
private fun TextInputForm(
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

@Composable
private fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = Modifier
            .padding(vertical = 32.dp)
            .size(width = 256.dp, height = 48.dp),
        colors = ButtonColors(
            containerColor = Magenta80,
            contentColor = Color.White,
            disabledContainerColor = Magenta80,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            stringResource(string.make_login),
            style = Typography.labelLarge
        )
    }
}

@Composable
private fun ForgetPasswordButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        TextButton(
            onClick = onClick,

        ) {
            Text(
                stringResource(string.forget_password),
                style = Typography.titleMedium,
                color = Blue80
            )
        }
    }
}