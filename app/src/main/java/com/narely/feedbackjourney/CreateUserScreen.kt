package com.narely.feedbackjourney

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.codicons.R
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserScreen() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text("Create User") },
            icon = { },
            onClick = {
                showBottomSheet = true
            }
        )
    }) { content ->
        if (showBottomSheet) {
            ModalBottomSheet(onDismissRequest = { showBottomSheet = false}, sheetState = sheetState) {
                Row() {
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Close Sheet")
                    }
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Save Forms")
                    }
                }
                FormCreateEditUserScreen()
            }
        }
        ListUsersScreen()
    }
}

@Composable
fun FormCreateEditUserScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        TextInputForm("Name")
        TextInputForm("Email")
        TextInputForm("Password")
        SelectedTypeUser()
        SelectedPDMUser()

    }
}

@Composable
fun TextInputForm(label: String) {
    val textFieldState = rememberTextFieldState(initialText = label)
    TextField(
        state = textFieldState,
        label = { Text(label) },
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ChooseUser(option: String) {
    val textFieldState = rememberTextFieldState(initialText = "")
    TextField(
        state = textFieldState,
        label = { Text(option) },
        placeholder = { Text(option) },
        readOnly = true,
        enabled = false
    )
}
//dropdown
@Composable
fun MenuSelected(typeUser: String, onClick: () -> Unit) {
    Row() {
        ChooseUser(typeUser)
        IconButton(onClick = onClick) {
            Icon(
                painterResource(R.drawable.codicons_ic_chevron_down),
                contentDescription = null
            )
        }
    }
}

@Composable
fun DropDownUserSelected(expanded: Boolean, listItems: List<String>, onDismiss: () -> Unit, onClick: (String) -> Unit) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        listItems.forEach { option ->
            DropdownMenuItem(
                text = { Text(option) },
                onClick = { onClick(option) }
            )
        }
    }
}

@Composable
fun SelectedTypeUser() {
    var expanded by remember { mutableStateOf(false) }
    var typeUser by remember { mutableStateOf("Choose a type") }
    val menuTypeUser: List<String> = listOf("Admin", "Collaborator", "PDM")
    Box() {
        MenuSelected(
            typeUser
        ) { expanded = !expanded }

        DropDownUserSelected(expanded,
            menuTypeUser,
            { expanded = false },
            { option ->
                typeUser = option
                expanded = false
            }
        )
    }
}

@Composable
fun SelectedPDMUser() {
    var expanded by remember { mutableStateOf(false) }
    var typeUser by remember { mutableStateOf("Choose a PDM") }
    val menuPDMEmail: List<String> = listOf("maria@ciandt.com", "joao@ciandt.com", "fernando@ciandt.com")

    Box() {
        MenuSelected(
            typeUser
        ) { expanded = !expanded }

        DropDownUserSelected(expanded,
            menuPDMEmail,
            { expanded = false },
            { option ->
                typeUser = option
                expanded = false
            }
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateUserScreenPreview() {
    FeedbackJourneyTheme {
        CreateUserScreen()
    }
}