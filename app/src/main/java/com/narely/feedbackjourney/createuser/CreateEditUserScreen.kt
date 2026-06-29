package com.narely.feedbackjourney.createuser

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.composables.icons.codicons.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditUserScreen(userId: String?, onFinishedActivity: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = if (userId == null) "Create User" else "Edit User") },
            navigationIcon = {
                BackFormCreateUser(onFinishedActivity)
            }
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            FormCreateEditUserScreen(userId, onFinishedActivity)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackFormCreateUser(onFinishedActivity: () -> Unit) {
    IconButton(onClick = { onFinishedActivity.invoke() }) {
        Icon(
            painterResource(R.drawable.codicons_ic_arrow_left),
            contentDescription = "Back",
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun SaveButtonCreateEditUser(onClick: () -> Unit) {
    Button(onClick = {
        onClick.invoke()
    }, modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 16.dp)
        .fillMaxWidth()
    ) {
        Text(stringResource(com.narely.feedbackjourney.R.string.save_user))
    }
}

@Composable
fun FormCreateEditUserScreen(userId: String?, onFinishedActivity: () -> Unit) {
    val currentUser = UserSingleton.readUser(userId)
    var initialName: String = "Name"
    var initialEmail: String = "Email"
    var initialPassword: String = "Password"
    if (currentUser != null) {
        initialName = currentUser.name
        initialEmail = currentUser.email
        initialPassword = currentUser.password
    }
    val nameTextFieldState = rememberTextFieldState(initialText = initialName)
    val emailTextFieldState = rememberTextFieldState(initialText = initialEmail)
    val passwordTextFieldState = rememberTextFieldState(initialText = initialPassword)
    Column() {
        TextInputForm(nameTextFieldState)
        TextInputForm(emailTextFieldState)
        TextInputForm(passwordTextFieldState)
        ChooseTypeUser()
        ChoosePDMUser()
        SaveButtonCreateEditUser {
            if (currentUser != null && userId != null) {
                UserSingleton.editUser(userId,
                    nameTextFieldState.text.toString(),
                    emailTextFieldState.text.toString(),
                    passwordTextFieldState.text.toString())
            } else {
                UserSingleton.createUser(
                    nameTextFieldState.text.toString(),
                    emailTextFieldState.text.toString(),
                    passwordTextFieldState.text.toString()
                )
            }
            onFinishedActivity.invoke()
        }
    }
}

@Composable
fun TextInputForm(valueState: TextFieldState) {
    OutlinedTextField(
        value = valueState.text.toString(),
        onValueChange = {
            valueState.setTextAndPlaceCursorAtEnd(it)
        },
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownChooseUsers(label: String, options: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    val textFieldState = rememberTextFieldState("")
    var checkedIndex: Int by remember { mutableIntStateOf(0) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it}, modifier =
        Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable)
                .fillMaxWidth(),
            state = textFieldState,
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MenuDefaults.containerColor,
            shape = MenuDefaults.shape
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        textFieldState.setTextAndPlaceCursorAtEnd(option)
                        checkedIndex = index
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun ChooseTypeUser() {
    val options: List<String> = listOf("Admin", "Collaborator", "PDM")
    DropDownChooseUsers("Choose a type", options)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoosePDMUser() {
    val options: List<String> = listOf("maria@ciandt.com", "joao@ciandt.com", "fernando@ciandt.com")
    DropDownChooseUsers("Choose a PDM", options)
}