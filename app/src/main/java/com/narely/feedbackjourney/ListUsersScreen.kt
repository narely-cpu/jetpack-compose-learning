package com.narely.feedbackjourney

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.codicons.R
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme

@Composable
fun ListUsersScreen() {
    val listUsers: List<String> = listOf("User 1", "User 2", "User 3","User 4","User 5","User 6","User 7","User 8")
    LazyColumn(modifier = Modifier.padding(12.dp)) {
        items(listUsers) { user ->
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth()) {
                ActionButtonsUser(user)
            }
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
fun ActionButtonsUser(user: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(user, modifier = Modifier.align(Alignment.CenterStart))
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            ButtonEditDelete("Edit",
                R.drawable.codicons_ic_edit) { }
            ButtonEditDelete("Delete",
                R.drawable.codicons_ic_trash) { }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListUsersScreenPreview() {
    FeedbackJourneyTheme {
        ListUsersScreen()
    }
}