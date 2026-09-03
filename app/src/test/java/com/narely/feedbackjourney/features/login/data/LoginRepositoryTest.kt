package com.narely.feedbackjourney.features.login.data

import com.narely.feedbackjourney.features.login.data.remote.LoginApi
import com.narely.feedbackjourney.features.login.data.remote.model.LoginRequest
import com.narely.feedbackjourney.features.login.data.remote.model.LoginResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class LoginRepositoryTest {

    lateinit var loginRepositoryImpl: LoginRepositoryImpl
    //
    val loginApi: LoginApi = mockk()


    @Before
    fun setup() {
        loginRepositoryImpl = LoginRepositoryImpl(loginApi = loginApi)
    }

    @Test
    fun `GIVEN a valid email and password WHEN login() is called THEN validate result as a token`() {
        runTest {
            // GIVEN
            val request = LoginRequest(
                email = "savi@ciandt.com",
                password = "Collaborator123!"
            )
            val response = LoginResponse(
                token = "oioioioioioioioi"
            )

            coEvery { loginApi.login(request) } returns response

            // WHEN
            val result = loginRepositoryImpl.login(request)

            // THEN
            Assertions.assertEquals(response.token, result.token)
            coVerify { loginApi.login(request) }
        }
    }

    @Test
    fun `GIVEN an invalid email and password WHEN login() is called THEN validate result as token is empty`() {
        runTest {
            // GIVEN
            val request = LoginRequest(
                email = "savi@ciandt.com",
                password = "Collaborator123!"
            )
            val response = LoginResponse(
                token = ""
            )

            coEvery { loginApi.login(request) } returns response

            // WHEN
            val result = loginRepositoryImpl.login(request)

            // THEN
            Assertions.assertEquals(response.token, result.token)
            coVerify { loginApi.login(request) }
        }
    }
}