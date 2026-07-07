package com.narely.feedbackjourney

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.composables.icons.codicons.R
import com.narely.feedbackjourney.createuser.CreateEditUserActivity
import com.narely.feedbackjourney.createuser.UserDataModel
import com.narely.feedbackjourney.R.string

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ListUsersScreen(viewModel: ListUsersViewModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState = viewModel.uiState.collectAsState().value
    val openAlertDialog = remember { mutableStateOf(false) }

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.updateList()
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        LazyColumn(modifier = Modifier.background(Color.LightGray)) {
            items(uiState.list) { user ->
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .fillMaxWidth()) {
                    ActionButtonsUser(user) { showDialog ->
                        viewModel.updateCurrentUser(user)
                        openAlertDialog.value = showDialog
                    }
                }
            }
        }
    }

    when {
        openAlertDialog.value -> {
            AlertDialogDeleteUser(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    viewModel.deleteUser(uiState.currentUser?.id ?: "")
                    viewModel.updateList()
                    openAlertDialog.value = false
                }
            )
        }
    }
}

@Composable
private fun ButtonEditDelete(description: String, icon: Int, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painterResource(icon),
            contentDescription = description,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
private fun ActionButtonsUser(user: UserDataModel, showAlertDeleteUser: (Boolean) -> Unit ) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(user.name, modifier = Modifier.align(Alignment.CenterStart))
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            ButtonEditDelete(stringResource(string.edit_user),
                R.drawable.codicons_ic_edit) {
                context.startActivity(Intent(context, CreateEditUserActivity::class.java)
                    .apply { putExtra("CURRENT_USER_ID", user.id) })
            }
            ButtonEditDelete(stringResource(string.delete_dialog_title),
                R.drawable.codicons_ic_trash) { showAlertDeleteUser(true) }
        }
    }
}

@Composable
private fun AlertDialogDeleteUser(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        title = {
            Text(stringResource(string.delete_dialog_title))
        },
        text = {
            Text(stringResource(string.delete_dialog_text))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton (
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(string.delete_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(string.cancel_button))
            }
        }
    )
}