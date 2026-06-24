package com.narely.feedbackjourney

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme
import com.composables.icons.codicons.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserScreen(context: Context) {

    Scaffold(topBar = {
        TopAppBar(
            title = { },
            navigationIcon = {
                BackFormCreateUser(context)
            }
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            FormCreateEditUserScreen()
            SaveButtonCreateUser(context)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackFormCreateUser(context: Context) {
    IconButton(onClick = {
        context.startActivity(Intent(context, MainActivity::class.java))
    }) {
        Icon(
            painterResource(R.drawable.codicons_ic_arrow_left),
            contentDescription = "Back",
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
fun SaveButtonCreateUser(context: Context) {
    Button(onClick = {
        context.startActivity(Intent(context, MainActivity::class.java))
    }, modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 16.dp)
        .fillMaxWidth()
    ) {
        Text("Save User")
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
        CreateUserScreen(LocalContext.current)
//        FormCreateEditUserScreen()
    }

}