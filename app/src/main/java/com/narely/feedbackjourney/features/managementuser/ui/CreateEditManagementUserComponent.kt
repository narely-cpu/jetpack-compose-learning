package com.narely.feedbackjourney.features.managementuser.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.R.string
import com.narely.feedbackjourney.commons.ui.TextInputFormComponent
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.managementuser.domain.model.UserTypeEnum
import com.narely.feedbackjourney.ui.theme.Blue80
import com.narely.feedbackjourney.ui.theme.Magenta80
import com.narely.feedbackjourney.ui.theme.Purple40
import com.narely.feedbackjourney.ui.theme.Purple80
import com.narely.feedbackjourney.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditManagementUserComponent(
    collaborator: UserDataModel?,
    updateShowModal: (Boolean) -> Unit,
    isButtonEnable: Boolean,
    handleConfirmCreateEditUserAction: () -> Unit,
    listPdm: List<UserDataModel>?,
    updateUiName: (String) -> Unit,
    updateUiEmail: (String) -> Unit,
    updateUiUserType: (String) -> Unit,
    updateUiPdmEmail: (String) -> Unit,
    isCollaborator: Boolean,
    errorMessage: String?,
) {

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp }
    val height = screenHeightPx.value * 0.7
    val title = if (collaborator?.id == 0) stringResource(string.new_collaborator) else stringResource(string.edit_collaborador)

    Scaffold(
        topBar = {
            TopBarCreateEditUser(
                title = title,
                closeModal = { updateShowModal(false) }
            )
        },
        bottomBar = {
            BottomBarCreateEditUser(
                userId = collaborator?.id,
                enabled = isButtonEnable,
                handleConfirmCreateEditUserAction = handleConfirmCreateEditUserAction
            )
        },
        containerColor = Color.White,
        modifier = Modifier.height(height.dp)
    ) { innerPadding ->
        Column(modifier = Modifier.padding(paddingValues = innerPadding)) {
            FormCreateEditUserContent(
                userName = collaborator?.name ?: "",
                userEmail = collaborator?.email ?: "",
                userType = collaborator?.type,
                userPdmEmail = collaborator?.pdmEmail,
                listPdm = listPdm?.map { it.email },
                onUserNameChange = { updateUiName(it) },
                onUserEmailChange = { updateUiEmail(it) },
                onUserTypeChange = { updateUiUserType(it) },
                onUserPdmEmailChange = { updateUiPdmEmail(it) },
                isCollaborator = isCollaborator
            )

            errorMessage?.let {
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
                text = title,
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
private fun BottomBarCreateEditUser(
    userId: Int?,
    enabled: Boolean,
    handleConfirmCreateEditUserAction: () -> Unit,
) {
    val buttonTitle = if (userId == 0) stringResource(string.add_collaborator) else stringResource(string.edit_collaborador)

    BottomAppBar(
        modifier = Modifier.height(99.dp),
        containerColor = Color.White
    ) {
        Button(
            onClick = handleConfirmCreateEditUserAction,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonColors(
                containerColor = Purple80,
                contentColor = Color.White,
                disabledContainerColor = Purple40,
                disabledContentColor = Color.White
            ),
            enabled = enabled,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = buttonTitle,
                style = Typography.labelLarge
            )
        }
    }
}

@Composable
private fun FormCreateEditUserContent(
    userName: String,
    userEmail: String,
    userType: UserTypeEnum?,
    userPdmEmail: String?,
    listPdm: List<String>?,
    onUserNameChange: (String) -> Unit,
    onUserEmailChange: (String) -> Unit,
    onUserTypeChange: (String) -> Unit,
    onUserPdmEmailChange: (String) -> Unit,
    isCollaborator: Boolean
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        TextInputFormComponent(
            label = null,
            placeholder = stringResource(string.name_label),
            valueState = userName,
            trailingIcon = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            updateValueState = onUserNameChange
        )
        TextInputFormComponent(
            label = null,
            placeholder = stringResource(string.email_placeholder),
            valueState = userEmail,
            trailingIcon = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
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
        TextInputFormComponent(
            label = null,
            placeholder = placeholder,
            valueState = valueState ?: "",
            updateValueState = updateValueState,
            trailingIcon = {
                IconButton(onClick = { expanded = (expanded && isCollaborator) }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "open",
                        tint = Magenta80
                    )
                }
            },
            readOnly = true,
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable)
                .fillMaxWidth(),
        )

        ExposedDropdownMenu(
            expanded = (expanded && isCollaborator),
            onDismissRequest = { expanded = false },
            containerColor = Color.White,
            shape = MenuDefaults.shape
        ) {
            options?.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style = Typography.labelMedium,
                            color = Color.Black
                        )
                    },
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
        options = options,
        placeholder = stringResource(string.choose_type_label),
        valueState = valueState,
        updateValueState = updateValueState
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
    FormCreateEditUserContent(
        userName = "",
        userEmail = "",
        userType = null,
        userPdmEmail = "",
        listPdm = emptyList(),
        onUserNameChange = {  },
        onUserEmailChange = {  },
        onUserTypeChange = {},
        onUserPdmEmailChange = {  },
        isCollaborator = true
    )
}