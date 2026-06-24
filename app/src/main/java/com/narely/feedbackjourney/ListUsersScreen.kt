package com.narely.feedbackjourney

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.codicons.R
import com.narely.feedbackjourney.createuser.CreateUserActivity
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme

@Composable
fun ListUsersScreen(context: Context) {
    val listUsers: List<String> = listOf("User 1", "User 2", "User 3","User 4","User 5","User 6","User 7","User 8")
    val openAlertDialog = remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.padding(12.dp)) {
        items(listUsers) { user ->
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth()) {
                ActionButtonsUser(user, context) { showDialog ->
                    openAlertDialog.value = showDialog
                }
            }
        }
    }

    when {
        openAlertDialog.value -> {
            AlertDialogDeleteUser(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                },
                dialogTitle = "Delete User",
                dialogText = "Are you sure you want to delete this user?",
            )
        }
    }
}

@Composable
fun ButtonEditDelete(description: String, icon: Int, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painterResource(icon),
            contentDescription = description,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
fun ActionButtonsUser(user: String, context: Context, showAlertDeleteUser: (Boolean) -> Unit ) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(user, modifier = Modifier.align(Alignment.CenterStart))
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            ButtonEditDelete("Edit",
                R.drawable.codicons_ic_edit) { context.startActivity(Intent(context, CreateUserActivity::class.java)) }
            ButtonEditDelete("Delete",
                R.drawable.codicons_ic_trash) { showAlertDeleteUser(true) }
        }
    }
}

@Composable
fun AlertDialogDeleteUser(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
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
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListUsersScreenPreview() {
    FeedbackJourneyTheme {
        ListUsersScreen(LocalContext.current)
    }
}