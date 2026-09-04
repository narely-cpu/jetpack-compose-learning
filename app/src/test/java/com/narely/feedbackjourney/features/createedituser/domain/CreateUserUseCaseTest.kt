package com.narely.feedbackjourney.features.createedituser.domain

import com.narely.feedbackjourney.features.createedituser.data.CreateEditUserRepositoryImpl
import com.narely.feedbackjourney.features.managementuser.data.remote.model.CreateEditUserRequest
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.managementuser.domain.model.UserTypeEnum
import com.narely.feedbackjourney.features.managementuser.domain.CreateUserUseCase
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
    private lateinit var createEditUserRepositoryImpl: CreateEditUserRepositoryImpl

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
            val request = CreateEditUserRequest(
                name = "savi",
                email = "savi@ciandt.com",
                type = UserTypeEnum.PDM.name,
                pdmId = null,
            )
            val pdmUser = UserDataModel(
                id = 1,
                name = "savi",
                email = "savi@ciandt.com",
                type = UserTypeEnum.PDM,
                pdmEmail = null,
            )
            val collaboratorUser = UserDataModel(
                id = 1,
                name = "lucas",
                email = "lucas@ciandt.com",
                type = UserTypeEnum.COLLABORATOR,
                pdmEmail = pdmUser.email,
            )

            coJustRun { createEditUserRepositoryImpl.createUser(request = request) }

            // WHEN
            createUserUseCase.invoke(
                collaborator = collaboratorUser,
                pdm = pdmUser,
                finishedActivityCreateUser = {},
                errorMessage = {}
            )

            // THEN
            coVerify { createEditUserRepositoryImpl.createUser(request = request) }
        }
}