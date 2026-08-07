package com.narely.feedbackjourney.createedituser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.narely.feedbackjourney.createedituser.ui.CreateEditUserScreen
import com.narely.feedbackjourney.createedituser.ui.CreateEditUserViewModel
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateEditUserActivity : ComponentActivity() {

    val createEditViewModel: CreateEditUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeedbackJourneyTheme {
                val userId = intent.getIntExtra("CURRENT_USER_ID", 0)

                CreateEditUserScreen(userId, createEditViewModel) {
                    finish()
                }
            }
        }
    }
}