package com.narely.feedbackjourney.createedituser.domain

import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import com.narely.feedbackjourney.createedituser.ui.UserTypeEnum
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class EditUserUseCaseTest {

    @MockK
    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

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
            val request = UpdateUserRequest(
                name = "saviolli",
                email = "savi@ciandt.com",
                type = UserTypeEnum.PDM.userValue,
                pdmId = null
            )

            coEvery { usersRepositoryImpl.getListPdm() } returns emptyList()
            coJustRun { usersRepositoryImpl.updateUser(id = userId, request = request) }

            //WHEN
            editUserUseCase.invoke(
                id = userId,
                name = request.name,
                email = request.email,
                userType = request.type,
                pdmEmail = null,
                finishedActivityCreateUser = {},
                errorMessage = {}
            )

            //THEN
            coVerify { usersRepositoryImpl.updateUser(id = userId, request = request) }
        }
}