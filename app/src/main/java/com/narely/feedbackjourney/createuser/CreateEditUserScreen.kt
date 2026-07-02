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
private fun BackFormCreateUser(onFinishedActivity: () -> Unit) {
    IconButton(onClick = { onFinishedActivity.invoke() }) {
        Icon(
            painterResource(R.drawable.codicons_ic_arrow_left),
            contentDescription = "Back",
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun SaveButtonCreateEditUser(isFormValid: Boolean, onClick: () -> Unit) {
    Button(onClick = {
        onClick.invoke()
    }, modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 16.dp)
        .fillMaxWidth(),
        enabled = isFormValid
    ) {
        Text("Save User")
    }
}

@Composable
private fun FormCreateEditUserScreen(userId: String?, onFinishedActivity: () -> Unit) {
    val currentUser = UserSingleton.readUser(userId)
    var initialName: String = "Name"
    var initialEmail: String = "Email"
    var initialPassword: String = "Password"
    var initialUserType: String = ""
    var initialPdmEmail: String = ""
    if (currentUser != null) {
        initialName = currentUser.name
        initialEmail = currentUser.email
        initialPassword = currentUser.password
        initialUserType = currentUser.userType.userValue
        initialPdmEmail = UserSingleton.getEmailById(currentUser.pdmId).toString()
    }
    val nameTextFieldState = rememberTextFieldState(initialText = initialName)
    val emailTextFieldState = rememberTextFieldState(initialText = initialEmail)
    val passwordTextFieldState = rememberTextFieldState(initialText = initialPassword)
    val userTypeTextFieldState = rememberTextFieldState(initialText = initialUserType)
    val pdmEmailTextFieldState = rememberTextFieldState(initialText = initialPdmEmail)

    Column() {
        TextInputForm(nameTextFieldState)
        TextInputForm(emailTextFieldState)
        TextInputForm(passwordTextFieldState)
        ChooseTypeUser(userTypeTextFieldState)
        ChoosePDMUser(pdmEmailTextFieldState, userTypeTextFieldState.text.toString())
        SaveButtonCreateEditUser(UserSingleton.isFormValid(nameTextFieldState.text.toString(),
            emailTextFieldState.text.toString(),
            passwordTextFieldState.text.toString(),
            userTypeTextFieldState.text.toString(),
            pdmEmailTextFieldState.text.toString()
        )) {
            if (currentUser != null && userId != null) {
                UserSingleton.editUser(userId,
                    nameTextFieldState.text.toString(),
                    emailTextFieldState.text.toString(),
                    passwordTextFieldState.text.toString(),
                    userTypeTextFieldState.text.toString(),
                    pdmEmailTextFieldState.text.toString()
                    )
            } else {
                UserSingleton.createUser(
                    nameTextFieldState.text.toString(),
                    emailTextFieldState.text.toString(),
                    passwordTextFieldState.text.toString(),
                    userTypeTextFieldState.text.toString(),
                    pdmEmailTextFieldState.text.toString()
                )
            }
            onFinishedActivity.invoke()
        }
    }
}

@Composable
private fun TextInputForm(valueState: TextFieldState) {
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
private fun DropDownChooseUsers(label: String, isEnable: Boolean, options: List<String>, valueState: TextFieldState) {
    var expanded by remember { mutableStateOf(false) }
    var checkedIndex: Int by remember { mutableIntStateOf(0) }
    ExposedDropdownMenuBox(expanded = (expanded && isEnable), onExpandedChange = { expanded = it }, modifier =
        Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable)
                .fillMaxWidth(),
            state = valueState,
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = (expanded && isEnable)) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = (expanded && isEnable),
            onDismissRequest = { expanded = false },
            containerColor = MenuDefaults.containerColor,
            shape = MenuDefaults.shape
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        valueState.setTextAndPlaceCursorAtEnd(option)
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
private fun ChooseTypeUser(valueState: TextFieldState) {
    val options: List<String> = listOf("Admin", "Collaborator", "PDM")
    DropDownChooseUsers("Choose a type", true, options, valueState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChoosePDMUser(valueState: TextFieldState, userType: String?) {
    val options = UserSingleton.getListPdm()
    DropDownChooseUsers("Choose a PDM", UserSingleton.isCollaborator(userType), options, valueState)
}