package com.narely.feedbackjourney.createuser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme

class CreateEditUserActivity: ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeedbackJourneyTheme {
                val userId = intent.getStringExtra("CURRENT_USER_ID")
                val viewModel: CreateEditUserViewModel = CreateEditUserViewModel()
                CreateEditUserScreen(userId, viewModel)  {
                    finish()
                }
            }
        }
    }
}