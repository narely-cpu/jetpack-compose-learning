package com.narely.feedbackjourney

//noinspection SuspiciousImport
import android.R
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.narely.feedbackjourney.features.login.ui.LoginScreen
import com.narely.feedbackjourney.features.login.ui.LoginViewModel
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeedbackJourneyTheme {
                LoginScreen(loginViewModel)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    overrideActivityTransition(
                        OVERRIDE_TRANSITION_OPEN,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                }
            }
        }
    }
}