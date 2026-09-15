package com.narely.feedbackjourney.features.home.ui

import androidx.lifecycle.ViewModel
import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.managementuser.domain.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getUsersUseCase: GetUserUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<HomeViewState> =
        MutableStateFlow(HomeViewState())
    val uiState: StateFlow<HomeViewState> = _uiState

    private fun updateUiState(uiState: HomeViewState) {
        _uiState.value = uiState
    }

//    fun updateCurrentUser(user: UserResponse) {
//        updateUiState(
//            uiState.value.copy(currentUser = user)
//        )
//    }
}