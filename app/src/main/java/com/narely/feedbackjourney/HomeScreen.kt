package com.narely.feedbackjourney

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.narely.feedbackjourney.createuser.CreateUserActivity
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(context: Context, viewModel: ListUsersViewModel) {
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text("Create User") },
            icon = { },
            onClick = {
                context.startActivity(Intent(context, CreateUserActivity::class.java))
            }
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ListUsersScreen(context, viewModel)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    FeedbackJourneyTheme {
//        HomeScreen(LocalContext.current)
    }
}