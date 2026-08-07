package com.narely.feedbackjourney.createedituser.domain

import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import com.narely.feedbackjourney.createedituser.domain.model.UserDataModel
import com.narely.feedbackjourney.core.data.remote.model.UserResponse
import com.narely.feedbackjourney.createedituser.ui.UserTypeEnum
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class GetUserUseCaseTest {
    @MockK
    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    @InjectMockKs
    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN an existing userId WHEN invoke() is called THEN validate the result as the correct user`() =
        runTest {
            // GIVEN
            val userId = 1
            val userResponse = UserResponse(
                id = userId,
                name = "savi",
                email = "savi@ciandt.com",
                type = UserTypeEnum.PDM.userValue,
                pdmId = null,
                active = true
            )
            val item = UserDataModel(
                id = userResponse.id,
                name = userResponse.name,
                email = userResponse.email,
                password = "Collaborator123!",
                type = userResponse.type,
                pdmEmail = null,
            )

            coEvery { usersRepositoryImpl.getListPdm() } returns listOf(userResponse)
            coEvery { usersRepositoryImpl.getUser(userId) } returns userResponse

            // WHEN
            val result = getUserUseCase.invoke(userId)

            // THEN
            Assertions.assertEquals(item, result)
        }

    @Test
    fun `GIVEN userId is null WHEN invoke() is called THEN validate that the result is null`() =
        runTest {
            // GIVEN
            val incorrectId = 2

            coEvery { usersRepositoryImpl.getListPdm() } returns emptyList()
            coEvery { usersRepositoryImpl.getUser(incorrectId) } returns null

            // WHEN
            val result = getUserUseCase.invoke(incorrectId)

            // THEN
            Assertions.assertNull(result)
        }
}