package com.narely.feedbackjourney.home.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.R.string

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val formsUiState by viewModel.uiState.collectAsState()
    Scaffold() { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            FormLoginLayout(
                userEmail = formsUiState.email,
                userPassword = formsUiState.password,
                onUserEmailChange = { viewModel.updateUiEmail(it) },
                onUserPasswordChange = { viewModel.updateUiPassword(it) },
                onLogin = { viewModel.login() }
            )

        }
    }
}

@Composable
private fun SaveButtonCreateEditUser(onClick: () -> Unit) {
    Button(onClick = {
        onClick.invoke()
    }, modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 16.dp)
        .fillMaxWidth(),
    ) {
        Text(stringResource(string.save_user))
    }
}

@Composable
private fun FormLoginLayout(userEmail: String,
                            userPassword: String,
                            onUserEmailChange: (String) -> Unit,
                            onUserPasswordChange: (String) -> Unit,
                            onLogin: () -> Unit) {

    Column() {
        TextInputForm(userEmail, onUserEmailChange)
        TextInputForm(userPassword, onUserPasswordChange)
        SaveButtonCreateEditUser {
            onLogin.invoke()
        }
    }
}

@Composable
private fun TextInputForm(valueState: String, updateValueState: (String) -> Unit) {
    OutlinedTextField(
        value = valueState,
        onValueChange = updateValueState,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .fillMaxWidth()
    )
}