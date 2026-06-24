package com.narely.feedbackjourney.createuser

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.narely.feedbackjourney.CreateUserScreen
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme

class CreateUserActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeedbackJourneyTheme {
                CreateUserScreen(LocalContext.current)
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateUserPreview() {
    FeedbackJourneyTheme {
        CreateUserScreen(LocalContext.current)
    }
}