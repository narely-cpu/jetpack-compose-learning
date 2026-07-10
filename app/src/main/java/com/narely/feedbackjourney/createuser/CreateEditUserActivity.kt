package com.narely.feedbackjourney.createuser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.createuser.domain.CreateUserUseCase
import com.narely.feedbackjourney.createuser.domain.EditUserUseCase
import com.narely.feedbackjourney.createuser.domain.GetListPdmUseCase
import com.narely.feedbackjourney.createuser.domain.GetUserUseCase
import com.narely.feedbackjourney.ui.theme.FeedbackJourneyTheme

class CreateEditUserActivity: ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeedbackJourneyTheme {
                val userId = intent.getStringExtra("CURRENT_USER_ID")
                val usersRepository = UsersRepository()
                val createUserUseCase = CreateUserUseCase(usersRepository)
                val editUserUseCase = EditUserUseCase(usersRepository)
                val getUserUseCase = GetUserUseCase(usersRepository)
                val getListPdmUseCase = GetListPdmUseCase(usersRepository)
                val viewModel = CreateEditUserViewModel(createUserUseCase,
                    editUserUseCase,
                    getUserUseCase,
                    getListPdmUseCase)
                CreateEditUserScreen(userId, viewModel)  {
                    finish()
                }
            }
        }
    }
}