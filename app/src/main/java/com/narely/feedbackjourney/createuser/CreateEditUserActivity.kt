package com.narely.feedbackjourney.createuser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
                val userId = intent.getStringExtra("CURRENT_USER_ID")
                CreateEditUserScreen(userId, createEditViewModel)  {
                    finish()
                }
            }
        }
    }
}