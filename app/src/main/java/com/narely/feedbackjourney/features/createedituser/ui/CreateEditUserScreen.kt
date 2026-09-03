package com.narely.feedbackjourney.features.createedituser.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.R.string
import com.narely.feedbackjourney.commons.ui.TextInputForm
import com.narely.feedbackjourney.features.createedituser.domain.model.UserTypeEnum
import com.narely.feedbackjourney.features.managementuser.ui.ManagementUserViewModel
import com.narely.feedbackjourney.ui.theme.Blue80
import com.narely.feedbackjourney.ui.theme.Magenta80
import com.narely.feedbackjourney.ui.theme.Purple80
import com.narely.feedbackjourney.ui.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditUserScreen(
    userId: Int,
    viewModel: ManagementUserViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.onCreateUiCreateEditView(userId)
    }

    val formsUiState by viewModel.uiState.collectAsState()
    val title = if (formsUiState.collaborator.id == 0) stringResource(string.new_collaborator) else stringResource(string.edit_collaborador)

    Scaffold(
        topBar = { TopBarCreateEditUser(
            title = title,
            closeModal = { viewModel.updateShowModal(false)} )
        },
        bottomBar = { BottomBarCreateEditUser(viewModel) },
        containerColor = Color.White,
        modifier = Modifier.height(770.dp),
    ) { innerPadding ->
        Column(modifier = Modifier.padding(paddingValues = innerPadding)) {
            FormCreateEditUserLayout(
                userId = formsUiState.collaborator.id,
                userName = formsUiState.collaborator.name,
                userEmail = formsUiState.collaborator.email,
                userType = formsUiState.collaborator.type,
                userPdmEmail = formsUiState.collaborator.pdmEmail,
                listPdm = formsUiState.listPdm?.map { it.email },
                onUserNameChange = { viewModel.updateUiName(it) },
                onUserEmailChange = { viewModel.updateUiEmail(it) },
                onUserTypeChange = { viewModel.updateUiUserType(it) },
                onUserPdmEmailChange = { viewModel.updateUiPdmEmail(it) },
                onCreateUser = { viewModel.createUser { viewModel.updateShowModal(false) } },
                onEditUser = { viewModel.editUser { viewModel.updateShowModal(false) } },
                isCollaborator = viewModel.isCollaborator(),
                isFormValid = viewModel.isButtonEnable()
            )

            formsUiState.errorMessage?.let {
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarCreateEditUser(title: String, closeModal: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                style = Typography.labelLarge,
                color = Blue80
            )
        },
        actions = {
            IconButton(onClick = { closeModal.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(string.back_button),
                    tint = Magenta80
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.White)
    )
}

@Composable
private fun BottomBarCreateEditUser(viewModel: ManagementUserViewModel) {
    BottomAppBar(
        modifier = Modifier.height(99.dp),
        containerColor = Color.White
    ) {
        Button(
            onClick = { viewModel.updateShowModal(false) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonColors(
                containerColor = Purple80,
                contentColor = Color.White,
                disabledContainerColor = Magenta80,
                disabledContentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                stringResource(string.add_collaborator),
                style = Typography.labelLarge
            )
        }
    }
}

//@Composable
//private fun SaveButtonCreateEditUser(isFormValid: Boolean, onClick: () -> Unit) {
//    Button(onClick = {
//        onClick.invoke()
//    }, modifier = Modifier
//        .padding(vertical = 4.dp, horizontal = 16.dp)
//        .fillMaxWidth(),
//        enabled = isFormValid
//    ) {
//        Text(stringResource(string.save_user))
//    }
//}

@Composable
private fun FormCreateEditUserLayout(
    userId: Int?,
    userName: String,
    userEmail: String,
    userType: UserTypeEnum?,
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

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        TextInputForm(
            label = null,
            placeholder = stringResource(string.name_label),
            valueState = userName,
            trailingIcon = null,
            updateValueState = onUserNameChange
        )
        TextInputForm(
            label = null,
            placeholder = stringResource(string.email_placeholder),
            valueState = userEmail,
            trailingIcon = null,
            updateValueState = onUserEmailChange
        )
        ChooseTypeUser(
            valueState = userType?.name,
            updateValueState = onUserTypeChange
        )
        ChoosePDMUser(
            valueState = userPdmEmail,
            isCollaborator = isCollaborator,
            updateValueState = onUserPdmEmailChange,
            listPdm = listPdm
        )
//        SaveButtonCreateEditUser(isFormValid) {
//            if (userId == 0) {
//                onCreateUser()
//            } else {
//                onEditUser()
//            }
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropDownChooseUsers(
    isCollaborator: Boolean,
    options: List<String>?,
    placeholder: String,
    valueState: String?,
    updateValueState: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = (expanded && isCollaborator),
        onExpandedChange = { expanded = it }
    ) {
        TextInputForm(
            label = null,
            placeholder = placeholder,
            valueState = valueState ?: "",
            updateValueState = updateValueState,
            trailingIcon = {
                IconButton(onClick = { expanded && isCollaborator }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "open",
                        tint = Magenta80
                    )
                }
            },
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
    valueState: String?,
    updateValueState: (String) -> Unit
) {
    val options: List<String> = listOf(
        UserTypeEnum.ADMIN.name,
        UserTypeEnum.COLLABORATOR.name,
        UserTypeEnum.PDM.name
    )

    DropDownChooseUsers(
        isCollaborator = true,
        options,
        placeholder = stringResource(string.choose_type_label),
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
        isCollaborator = isCollaborator,
        options = listPdm,
        placeholder = stringResource(string.choose_pdm_label),
        valueState = valueState,
        updateValueState = updateValueState
    )
}

@Preview
@Composable
private fun FormCreateEditUserLayoutPreview() {
    FormCreateEditUserLayout(
        userId = 0,
        userName = "",
        userEmail = "",
        userType = null,
        userPdmEmail = "",
        listPdm = emptyList(),
        onUserNameChange = {  },
        onUserEmailChange = {  },
        onUserTypeChange = {},
        onUserPdmEmailChange = {  },
        onCreateUser = {},
        onEditUser = {  },
        isCollaborator = true,
        isFormValid = true,
    )
}