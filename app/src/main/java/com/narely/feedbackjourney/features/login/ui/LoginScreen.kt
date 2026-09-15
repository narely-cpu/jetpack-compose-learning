package com.narely.feedbackjourney.features.login.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.narely.feedbackjourney.commons.ui.TextInputFormComponent
import com.narely.feedbackjourney.features.home.HomeActivity
import com.narely.feedbackjourney.ui.theme.Blue80
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
            onUserPasswordChange = { viewModel.updateUiPassword(newPassword = it) },
            loginOnClick = {
                viewModel.login()
                context.startActivity(
                    Intent(
                        context,
                        HomeActivity::class.java
                    )
                )
            }
        )
    }
}

@Composable
private fun FormLoginLayout(
    userEmail: String,
    userPassword: String,
    onUserEmailChange: (String) -> Unit,
    onUserPasswordChange: (String) -> Unit,
    loginOnClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 32.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TextInputFormComponent(
                    label = stringResource(string.email_label),
                    placeholder = null,
                    valueState = userEmail,
                    trailingIcon = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    updateValueState = onUserEmailChange
                )
                TextInputFormComponent(
                    label = stringResource(string.password_label),
                    placeholder = null,
                    valueState = userPassword,
                    trailingIcon = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    updateValueState = onUserPasswordChange
                )
            }
            ForgotPasswordButton(onClick = { })
        }
        LoginButton(onClick = loginOnClick)
    }
}

@Composable
private fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 49.dp),
        colors = ButtonColors(
            containerColor = Magenta80,
            contentColor = Color.White,
            disabledContainerColor = Magenta80,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 14.5.dp, horizontal = 85.dp)
    ) {
        Text(
            text = stringResource(string.login_button_label),
            style = Typography.labelLarge
        )
    }
}

@Composable
private fun ForgotPasswordButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        TextButton(onClick = onClick) {
            Text(
                stringResource(string.forgot_password),
                style = Typography.titleMedium,
                color = Blue80
            )
        }
    }
}