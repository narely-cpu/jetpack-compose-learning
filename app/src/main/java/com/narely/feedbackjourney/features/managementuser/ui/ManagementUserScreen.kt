package com.narely.feedbackjourney.features.managementuser.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.narely.feedbackjourney.R
import com.narely.feedbackjourney.R.string
import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.managementuser.domain.model.UserTypeEnum
import com.narely.feedbackjourney.ui.theme.Blue40
import com.narely.feedbackjourney.ui.theme.Blue80
import com.narely.feedbackjourney.ui.theme.Grey40
import com.narely.feedbackjourney.ui.theme.Grey80
import com.narely.feedbackjourney.ui.theme.Magenta80
import com.narely.feedbackjourney.ui.theme.Purple80
import com.narely.feedbackjourney.ui.theme.Typography

@Composable
fun ManagementUserScreen(
    managementUserViewModel: ManagementUserViewModel,
    onFinishedActivity: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState = managementUserViewModel.uiState.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    managementUserViewModel.updateList()
                }
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    ManagementUserContent(
        isLoading = uiState.value.isLoading,
        listUsers = uiState.value.listUsers,
        showModal = uiState.value.showModal,
        collaborator = uiState.value.collaborator,
        listPdm = uiState.value.listPdm,
        errorMessage = uiState.value.errorMessage,
        showAlert = uiState.value.showAlert,
        onFinishedActivity = onFinishedActivity,
        handleCreateUserForm = { managementUserViewModel.handleCreateUserForm() },
        getPdmNameById = { managementUserViewModel.getPdmNameById(pdmId = it) },
        handleEditUserForm = { managementUserViewModel.handleEditUserForm(userId = it) },
        handleRemoveUserAlert = { managementUserViewModel.handleRemoveUserAlert(userId = it) },
        updateShowModal = { managementUserViewModel.updateShowModal(it) },
        isButtonEnable = { managementUserViewModel.isButtonEnable() },
        handleConfirmCreateEditUserAction = { managementUserViewModel.handleConfirmCreateEditUserAction() },
        updateUiName = { managementUserViewModel.updateUiName(newName = it) },
        updateUiEmail = { managementUserViewModel.updateUiEmail(newEmail = it) },
        updateUiUserType = { managementUserViewModel.updateUiUserType(newUserType = it) },
        updateUiPdmEmail = { managementUserViewModel.updateUiPdmEmail(newPdmEmail = it) },
        isCollaborator = { managementUserViewModel.isCollaborator() },
        updateShowAlert = { managementUserViewModel.updateShowAlert(it) },
        removeUser = { managementUserViewModel.removeUser(uiState.value.collaborator.id) }
    )
}

@Composable
fun ManagementUserContent(
    isLoading: Boolean,
    listUsers: List<UserResponse>,
    showModal: Boolean,
    collaborator: UserDataModel,
    listPdm: List<UserDataModel>?,
    errorMessage: String?,
    showAlert: Boolean,
    onFinishedActivity: () -> Unit,
    handleCreateUserForm: () -> Unit,
    getPdmNameById: (Int?) -> String?,
    handleEditUserForm: (Int) -> Unit,
    handleRemoveUserAlert: (Int) -> Unit,
    updateShowModal: (Boolean) -> Unit,
    isButtonEnable: () -> Boolean,
    handleConfirmCreateEditUserAction: () -> Unit,
    updateUiName: (String) -> Unit,
    updateUiEmail: (String) -> Unit,
    updateUiUserType: (String) -> Unit,
    updateUiPdmEmail: (String) -> Unit,
    isCollaborator: () -> Boolean,
    updateShowAlert: (Boolean) -> Unit,
    removeUser: (Int) -> Unit
) {
    Scaffold(
        containerColor = Grey40,
        topBar = { TopBarManagementUser(onFinishedActivity) },
        bottomBar = { BottomBarManagementUser(handleCreateUserForm) }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues = innerPadding)
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(listUsers) { item ->
                    UserListItem(
                        name = item.name,
                        pdmName = getPdmNameById(item.pdmId),
                        updateUser = { handleEditUserForm(item.id) },
                        deleteUser = { handleRemoveUserAlert(item.id) }
                    )
                }
            }
            if (showModal) {
                CreateEditUsersModalScreen(
                    collaborator = collaborator,
                    updateShowModal = { updateShowModal(it) },
                    isButtonEnable = isButtonEnable(),
                    handleConfirmCreateEditUserAction = { handleConfirmCreateEditUserAction() },
                    listPdm = listPdm,
                    updateUiName = { updateUiName(it) },
                    updateUiEmail = { updateUiEmail(it) },
                    updateUiUserType = { updateUiUserType(it) },
                    updateUiPdmEmail = { updateUiPdmEmail(it) },
                    isCollaborator = isCollaborator(),
                    errorMessage = errorMessage
                )
            }
            if (showAlert) {
                AlertDialogDeleteUser(
                    onDismissRequest = { updateShowAlert(false) },
                    onConfirmation = { removeUser(collaborator.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarManagementUser(onFinishedActivity: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(string.manage_members),
                style = Typography.labelLarge,
                color = Blue80
            )
        },
        navigationIcon = {
            IconButton(onClick = { onFinishedActivity.invoke() }) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = stringResource(string.back_button),
                    tint = Magenta80
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.White)
    )
}

@Composable
private fun BottomBarManagementUser(handleCreateUserForm: () -> Unit) {
    Box(modifier = Modifier.background(Color.White)) {
        Button(
            onClick = handleCreateUserForm,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple80,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                stringResource(string.new_collaborator),
                style = Typography.labelLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
private fun UserListItem(
    name: String,
    pdmName: String?,
    updateUser: () -> Unit,
    deleteUser: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        InfoUser(name = name, pdmName = pdmName)
        ConfigUser(updateUser, deleteUser)
    }
}

@Composable
private fun InfoUser(name: String, pdmName: String?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PersonOutline,
            contentDescription = stringResource(id = string.image_profile),
            tint = Blue80,
            modifier = Modifier
                .size(24.dp)
                .background(color = Blue40, shape = CircleShape)
                .padding(4.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                name,
                style = Typography.displaySmall,
                color = Color.Black
            )

            pdmName?.let {
                Text(
                    text = "${stringResource(string.pdm_name)} $pdmName",
                    style = Typography.bodySmall,
                    color = Grey80
                )
            }
        }
    }
}

@Composable
private fun ConfigUser(updateUser: () -> Unit, deleteUser: () -> Unit) {
    Row(
        modifier = Modifier.padding(end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = deleteUser,
            modifier = Modifier.size(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.trash),
                contentDescription = stringResource(string.confirm_button)
            )
        }

        VerticalDivider(
            modifier = Modifier.height(20.5.dp),
            color = Grey40
        )

        IconButton(
            onClick = updateUser,
            modifier = Modifier.size(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.edit_user),
                contentDescription = stringResource(string.edit_collaborador)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateEditUsersModalScreen(
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
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { updateShowModal(false) },
        sheetState = sheetState,
        dragHandle = null
    ) {
        CreateEditManagementUserComponent(
            collaborator = collaborator,
            updateShowModal = updateShowModal,
            isButtonEnable = isButtonEnable,
            handleConfirmCreateEditUserAction = handleConfirmCreateEditUserAction,
            listPdm = listPdm,
            updateUiName = updateUiName,
            updateUiEmail = updateUiEmail,
            updateUiUserType = updateUiUserType,
            updateUiPdmEmail = updateUiPdmEmail,
            isCollaborator = isCollaborator,
            errorMessage = errorMessage
        )
    }
}

@Composable
private fun AlertDialogDeleteUser(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(string.delete_dialog_title),
                    style = Typography.labelLarge
                )
                IconButton(onClick = onDismissRequest) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(string.cancel_button),
                        tint = Magenta80
                    )
                }
            }
        },
        titleContentColor = Blue80,
        text = {
            Text(
                text = stringResource(string.delete_dialog_text),
                style = Typography.labelMedium
            )
        },
        textContentColor = Color.Black,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = stringResource(string.cancel_button),
                        style = Typography.labelLarge,
                        color = Blue80
                    )
                }

                Button(
                    onClick = onConfirmation,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Magenta80,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(string.confirm_button),
                        style = Typography.labelLarge
                    )
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

@Preview
@Composable
fun ManagementUserContentPreview() {
    val listUsers = listOf(
        UserResponse(
            id = 1,
            name = "name 1",
            email = "email1@ciandt.com",
            type = "PDM",
            pdmId = null,
            active = true
        ),
        UserResponse(
            id = 2,
            name = "name 2",
            email = "email2@ciandt.com",
            type = "PDM",
            pdmId = null,
            active = true
        ),
        UserResponse(
            id = 3,
            name = "name 3",
            email = "email3@ciandt.com",
            type = "COLLABORATOR",
            pdmId = 1,
            active = true
        ),
        UserResponse(
            id = 4,
            name = "name 4",
            email = "email4@ciandt.com",
            type = "COLLABORATOR",
            pdmId = 2,
            active = true
        ),
    )
    val listPdm = listOf(
        UserDataModel(
            id = 1,
            name = "nome 1",
            email = "email1@ciandt.com",
            type = UserTypeEnum.PDM,
            pdmEmail = null
        ),
        UserDataModel(
            id = 1,
            name = "nome 2",
            email = "email1@ciandt.com",
            type = UserTypeEnum.PDM,
            pdmEmail = null
        )
    )

    ManagementUserContent(
        isLoading = false,
        listUsers = listUsers,
        showModal = false,
        collaborator = UserDataModel(),
        listPdm = listPdm,
        errorMessage = null,
        showAlert = false,
        onFinishedActivity = { },
        handleCreateUserForm = { },
        getPdmNameById = { "nome 2" },
        handleEditUserForm = {},
        handleRemoveUserAlert = {},
        updateShowModal = { },
        isButtonEnable = { true },
        handleConfirmCreateEditUserAction = {},
        updateUiName = {},
        updateUiEmail = {},
        updateUiUserType = {},
        updateUiPdmEmail = {},
        isCollaborator = { false },
        updateShowAlert = {},
        removeUser = {},
    )
}