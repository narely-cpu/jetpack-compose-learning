package com.narely.feedbackjourney.features.createedituser.domain

import com.narely.feedbackjourney.features.createedituser.data.CreateEditUserRepositoryImpl
import com.narely.feedbackjourney.features.createedituser.data.remote.model.CreateEditUserRequest
import com.narely.feedbackjourney.features.createedituser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.createedituser.domain.model.UserTypeEnum
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class EditUserUseCaseTest {

    @MockK
    private lateinit var createEditUserRepositoryImpl: CreateEditUserRepositoryImpl

    @InjectMockKs
    private lateinit var editUserUseCase: EditUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN modified user information WHEN invoke() is called THEN validate that the repository's update function is called`() =
        runTest {
            //GIVEN
            val userId = 1
            val request = CreateEditUserRequest(
                name = "saviolli",
                email = "savi@ciandt.com",
                type = UserTypeEnum.PDM.name,
                pdmId = null
            )
            val collaboratorUser = UserDataModel(
                id = userId,
                name = "savi",
                email = "savi@ciandt.com",
                type = UserTypeEnum.PDM,
                pdmEmail = null,
            )

            coJustRun { createEditUserRepositoryImpl.updateUser(id = userId, request = request) }

            //WHEN
            editUserUseCase.invoke(
                collaborator = collaboratorUser,
                pdm = null,
                finishedActivityCreateUser = {},
                errorMessage = {}
            )

            //THEN
            coVerify { createEditUserRepositoryImpl.updateUser(id = userId, request = request) }
        }
}