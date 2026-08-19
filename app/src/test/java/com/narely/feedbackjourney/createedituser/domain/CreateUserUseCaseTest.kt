package com.narely.feedbackjourney.createedituser.domain

import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import com.narely.feedbackjourney.createedituser.data.remote.model.CreateUserRequest
import com.narely.feedbackjourney.features.createedituser.domain.CreateUserUseCase
import com.narely.feedbackjourney.features.createedituser.domain.model.UserTypeEnum
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.util.UUID

class CreateUserUseCaseTest {

    @RelaxedMockK
    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    @InjectMockKs
    private lateinit var createUserUseCase: CreateUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(UUID::class)
    }

    @Test
    fun `GIVEN a created user WHEN invoke() is called THEN validate that the repository's create function is called`() =
        runTest {
            // GIVEN
            val request = CreateUserRequest(
                name = "savi",
                email = "savi@ciandt.com",
                type = UserTypeEnum.PDM.userValue,
                pdmId = null,
            )

            coJustRun { usersRepositoryImpl.createUser(request = request) }

            // WHEN
            createUserUseCase.invoke(
                name = "savi",
                email = "savi@ciandt.com",
                userType = "PDM",
                pdmEmail = null,
                finishedActivityCreateUser = {},
                errorMessage = {}
            )

            // THEN
            coVerify { usersRepositoryImpl.createUser(request = request) }
        }
}