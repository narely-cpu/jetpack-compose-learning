package com.narely.feedbackjourney.core

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.narely.feedbackjourney.core.domain.GetUsersUseCase
import com.narely.feedbackjourney.home.HomeScreen
import com.narely.feedbackjourney.home.HomeViewModel
import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.home.domain.RemoveUserUseCase
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeedbackJourneyTheme {
                val usersRepository = UsersRepository()
                val getUsersUseCase = GetUsersUseCase(usersRepository)
                val removeUserUseCase = RemoveUserUseCase(usersRepository)
                HomeScreen(HomeViewModel(getUsersUseCase, removeUserUseCase))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    overrideActivityTransition(
                        OVERRIDE_TRANSITION_OPEN,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                    )
                }
            }
        }
    }
}