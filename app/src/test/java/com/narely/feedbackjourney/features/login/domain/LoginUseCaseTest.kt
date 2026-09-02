package com.narely.feedbackjourney.features.login.domain

import com.narely.feedbackjourney.features.login.data.LoginRepositoryImpl
import com.narely.feedbackjourney.features.login.data.remote.model.LoginRequest
import com.narely.feedbackjourney.features.login.data.remote.model.LoginResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class LoginUseCaseTest {
    @MockK
    private lateinit var loginRepositoryImpl: LoginRepositoryImpl

    @InjectMockKs
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN a valid email and password WHEN invoke() is called THEN it should return a valid token`() =
        runTest {
            // GIVEN
            val request = LoginRequest(
                email = "savi@ciandt.com",
                password = "Collaborator123!"
            )
            val response = LoginResponse(
                token = "oioioioioi"
            )

            coEvery { loginRepositoryImpl.login(request) } returns response

            // WHEN
            val result = loginUseCase.invoke(request.email, request.password, { it.orEmpty() }, {})

            // THEN
            Assertions.assertEquals(response.token, result?.token)
        }

    @Test
    fun `GIVEN an invalid email and password WHEN invoke() is called THEN it should return an error message`() =
        runTest {
            // GIVEN
            val request = LoginRequest(
                email = "savi@ciandt.com",
                password = "Collaborator123!"
            )
            val exception = Exception("Error logging into the user account")

            coEvery { loginRepositoryImpl.login(request) } throws exception

            // WHEN
            var message = ""
            val result = loginUseCase.invoke(request.email, request.password, {}, { message = it.orEmpty() })

            // THEN
            Assertions.assertEquals(null, result)
            Assertions.assertEquals(exception.message, message)
        }
}