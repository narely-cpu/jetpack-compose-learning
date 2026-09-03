package com.narely.feedbackjourney.features.managementuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.narely.feedbackjourney.features.createedituser.ui.CreateEditUserViewModel
import com.narely.feedbackjourney.features.managementuser.ui.ManagementUserScreen
import com.narely.feedbackjourney.features.managementuser.ui.ManagementUserViewModel
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class ManagementUserActivity : ComponentActivity()  {

    val managementUserViewModel: ManagementUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeedbackJourneyTheme {
                ManagementUserScreen(managementUserViewModel = managementUserViewModel) {
                    finish()
                }
            }
        }
    }
}