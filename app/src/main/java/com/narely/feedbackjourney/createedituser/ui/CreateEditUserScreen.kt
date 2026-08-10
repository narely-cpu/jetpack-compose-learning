package com.narely.feedbackjourney.createedituser.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.composables.icons.codicons.R
import com.narely.feedbackjourney.R.string

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditUserScreen(
    userId: Int,
    viewModel: CreateEditUserViewModel,
    onFinishedActivity: () -> Unit
) {
    LaunchedEffect(Unit) {
        if (userId != 0) viewModel.updateUiCurrentUser(userId)
        viewModel.getListPdm()
    }

    val formsUiState by viewModel.uiState.collectAsState()
    val title = if (formsUiState.id == null) stringResource(string.create_user) else stringResource(string.edit_user)

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                BackFormCreateUser(onFinishedActivity)
            }
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(paddingValues = innerPadding)) {
            FormCreateEditUserLayout(
                userId = formsUiState.id,
                userName = formsUiState.name,
                userEmail = formsUiState.email,
                userType = formsUiState.userType,
                userPdmEmail = formsUiState.pdmEmail,
                listPdm = formsUiState.listPdm,
                onUserNameChange = { viewModel.updateUiName(it) },
                onUserEmailChange = { viewModel.updateUiEmail(it) },
                onUserTypeChange = { viewModel.updateUiUserType(it) },
                onUserPdmEmailChange = { viewModel.updateUiPdmEmail(it) },
                onCreateUser = { viewModel.createUser(onFinishedActivity) },
                onEditUser = { viewModel.editUser(onFinishedActivity) },
                isCollaborator = viewModel.isCollaborator(),
                isFormValid = viewModel.isButtonEnable())

            formsUiState.errorMessage?.let {
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BackFormCreateUser(onFinishedActivity: () -> Unit) {
    IconButton(onClick = { onFinishedActivity.invoke() }) {
        Icon(
            painterResource(R.drawable.codicons_ic_arrow_left),
            contentDescription = stringResource(string.back_button),
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
        Text(stringResource(string.save_user))
    }
}

@Composable
private fun FormCreateEditUserLayout(
    userId: Int?,
    userName: String,
    userEmail: String,
    userType: String,
    userPdmEmail: String?,
    listPdm: List<String>?,
    onUserNameChange: (String) -> Unit,
    onUserEmailChange: (String) -> Unit,
    onUserTypeChange: (String) -> Unit,
    onUserPdmEmailChange: (String) -> Unit,
    onCreateUser: () -> Unit,
    onEditUser: () -> Unit,
    isCollaborator: Boolean,
    isFormValid: Boolean
) {

    Column() {
        TextInputForm(valueState = userName, updateValueState =onUserNameChange)
        TextInputForm(valueState = userEmail, updateValueState =onUserEmailChange)
        ChooseTypeUser(valueState = userType, updateValueState =onUserTypeChange)
        ChoosePDMUser(
            valueState = userPdmEmail,
            isCollaborator = isCollaborator,
            updateValueState = onUserPdmEmailChange,
            listPdm = listPdm
        )
        SaveButtonCreateEditUser(isFormValid) {
            if (userId == null) {
                onCreateUser()
            } else {
                onEditUser()
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropDownChooseUsers(
    label: String,
    isCollaborator: Boolean,
    options: List<String>?,
    valueState: String?,
    updateValueState: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = (expanded && isCollaborator),
        onExpandedChange = { expanded = it },
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable)
                .fillMaxWidth(),
            value = valueState ?: "",
            readOnly = true,
            onValueChange = updateValueState,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = (expanded && isCollaborator))
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            expanded = (expanded && isCollaborator),
            onDismissRequest = { expanded = false },
            containerColor = MenuDefaults.containerColor,
            shape = MenuDefaults.shape
        ) {
            options?.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        updateValueState(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
private fun ChooseTypeUser(
    valueState: String,
    updateValueState: (String) -> Unit
) {
    val options: List<String> = listOf(
        stringResource(string.admin_label),
        stringResource(string.collaborator_label),
        stringResource(string.pdm_label)
    )

    DropDownChooseUsers(stringResource(string.choose_type_label),
        isCollaborator = true,
        options,
        valueState,
        updateValueState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChoosePDMUser(
    valueState: String?,
    isCollaborator: Boolean,
    updateValueState: (String) -> Unit,
    listPdm: List<String>?
) {
    DropDownChooseUsers(
        label = stringResource(string.choose_pdm_label),
        isCollaborator = isCollaborator,
        options = listPdm,
        valueState = valueState,
        updateValueState = updateValueState
    )
}