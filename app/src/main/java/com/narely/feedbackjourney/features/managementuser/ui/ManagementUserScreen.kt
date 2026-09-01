package com.narely.feedbackjourney.features.managementuser.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.narely.feedbackjourney.R
import com.narely.feedbackjourney.R.string
import com.narely.feedbackjourney.ui.theme.Blue40
import com.narely.feedbackjourney.ui.theme.Blue80
import com.narely.feedbackjourney.ui.theme.Grey40
import com.narely.feedbackjourney.ui.theme.Grey80
import com.narely.feedbackjourney.ui.theme.Magenta80
import com.narely.feedbackjourney.ui.theme.Purple80
import com.narely.feedbackjourney.ui.theme.Typography

@Composable
fun ManagementUserScreen() {
    //TODO: ADD VIEWMODEL
    val userList = listOf(
        InfoUsers("Nome do Colaborador", "Nome do PDM"),
        InfoUsers("Nome do Colaborador", "Nome do PDM"),
        InfoUsers("Nome do Colaborador", "Nome do PDM"),
    )

    Scaffold(
        containerColor = Grey40,
        topBar = { TopBarManagementUser() },
        bottomBar = { BottomBarManagementUser() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(userList) { item ->
                UserListItem(item.name, item.pdmName)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarManagementUser() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(string.manage_members),
                style = Typography.labelLarge,
                color = Blue80
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = stringResource(string.back_button),
                tint = Magenta80
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.White)
    )
}

@Composable
private fun BottomBarManagementUser() {
    BottomAppBar(
        modifier = Modifier.height(99.dp),
        containerColor = Color.White
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            colors = ButtonColors(
                containerColor = Purple80,
                contentColor = Color.White,
                disabledContainerColor = Magenta80,
                disabledContentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                stringResource(string.new_collaborator),
                style = Typography.labelLarge
            )
        }
    }
}

@Composable
private fun UserListItem(name: String, pdmName: String?) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        InfoUser(name = name, pdmName = pdmName)
        ConfigUser()
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
            // TODO: Add a check so it doesn't appear if the user is not a collaborator
            Text(
                "PDM | ${pdmName}",
                style = Typography.bodySmall,
                color = Grey80
            )
        }
    }
}

@Composable
private fun ConfigUser() {
    Row(
        modifier = Modifier
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
        ) {
        IconButton(
            onClick = {},
            modifier = Modifier.size(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.trash),
                contentDescription = stringResource(string.delete_button)
            )
        }

        VerticalDivider(
            modifier = Modifier.height(20.5.dp),
            color = Grey40
        )

        IconButton(
            onClick = {},
            modifier = Modifier.size(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.edit_user),
                contentDescription = stringResource(string.edit_user)
            )
        }
    }
}

data class InfoUsers(val name: String, val pdmName: String?)

@Composable
@Preview
private fun ManagementUserScreenPreview() {
    ManagementUserScreen()
}