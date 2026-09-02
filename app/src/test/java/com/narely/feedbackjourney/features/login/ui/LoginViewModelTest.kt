package com.narely.feedbackjourney.features.login.ui

import com.narely.feedbackjourney.features.login.data.remote.model.LoginResponse
import com.narely.feedbackjourney.features.login.domain.LoginUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class LoginViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var loginUseCase: LoginUseCase

    @InjectMockKs
    private lateinit var loginViewModel: LoginViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN email changed WHEN updateUiEmail() is called THEN validate email was changed`() {
        // GIVEN
        val newEmail = "email@ciandt.com"

        // WHEN
        loginViewModel.updateUiEmail(newEmail)
        val currentUiEmail = loginViewModel.uiState.value.email

        // THEN
        Assertions.assertEquals(newEmail, currentUiEmail)
    }

    @Test
    fun `GIVEN password changed WHEN updateUiPassword() is called THEN validate password was changed`() {
        // GIVEN
        val newPassword = "Collaborator123!"

        // WHEN
        loginViewModel.updateUiPassword(newPassword)
        val currentUiPassword = loginViewModel.uiState.value.password

        // THEN
        Assertions.assertEquals(newPassword, currentUiPassword)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN login successful WHEN login() is called THEN validate token was changed`() =
        runTest {
            // GIVEN
            val newEmail = "email@ciandt.com"
            val newPassword = "Collaborator123!"
            val newToken = "token-novo-teste"

            loginViewModel.updateUiEmail(newEmail)
            loginViewModel.updateUiPassword(newPassword)

            val currentUiState = loginViewModel.uiState.value
            val tokenResponseCaptor = slot<(String?) -> Unit>()

            coEvery { loginUseCase.invoke(
                email = currentUiState.email,
                password = currentUiState.password,
                tokenResponse = capture(tokenResponseCaptor),
                errorMessage = any()
            )} returns LoginResponse(token = newToken)

            // WHEN
            loginViewModel.login()
            advanceUntilIdle()

            tokenResponseCaptor.captured.invoke(newToken)
            val currentUiToken = loginViewModel.uiState.value.token

            // THEN
            Assertions.assertEquals(newToken, currentUiToken)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN unsuccessful login attempt WHEN login() is called THEN validate error message was changed`() =
        runTest {
            // GIVEN
            val newEmail = "email@ciandt.com"
            val newPassword = "Collaborator123!"
            val newMessageError = "Error 404"

            loginViewModel.updateUiEmail(newEmail)
            loginViewModel.updateUiPassword(newPassword)

            val currentUiState = loginViewModel.uiState.value
            val errorMessageCaptor = slot<(String?) -> Unit>()

            coEvery { loginUseCase.invoke(
                email = currentUiState.email,
                password = currentUiState.password,
                tokenResponse = any(),
                errorMessage = capture(errorMessageCaptor)
            )} returns null

            // WHEN
            loginViewModel.login()
            advanceUntilIdle()

            errorMessageCaptor.captured.invoke(newMessageError)
            val currentUiErrorMessage = loginViewModel.uiState.value.errorMessage

            // THEN
            Assertions.assertEquals(newMessageError, currentUiErrorMessage)
        }
}