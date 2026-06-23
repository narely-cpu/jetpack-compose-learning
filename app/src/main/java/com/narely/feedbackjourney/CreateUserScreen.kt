package com.narely.feedbackjourney

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserScreen(onClickCreate: (Boolean) -> Unit) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onClickCreate(false) },
        sheetState = sheetState) {
        Row(modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            ActionsFormCreateUser("Close", sheetState, onClickCreate)
            ActionsFormCreateUser("Save", sheetState, onClickCreate)
        }
        FormCreateEditUserScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionsFormCreateUser(label: String, sheetState: SheetState, onClickCreate: (Boolean) -> Unit) {
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Button(onClick = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onClickCreate(false)
                showBottomSheet = false
            }
        }
    }) {
        Text(label)
    }
}

@Composable
fun FormCreateEditUserScreen() {
    Column() {
        TextInputForm("Name")
        TextInputForm("Email")
        TextInputForm("Password")
        ChooseTypeUser()
        ChoosePDMUser()
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateUserScreenPreview() {
    FeedbackJourneyTheme {
        CreateUserScreen {}
//        FormCreateEditUserScreen()
    }

}