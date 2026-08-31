package com.narely.feedbackjourney.features.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.narely.feedbackjourney.features.home.ui.HomeScreen
import com.narely.feedbackjourney.features.home.ui.HomeViewModel
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeedbackJourneyTheme {
                HomeScreen(viewModel = homeViewModel)
            }
        }
    }
}