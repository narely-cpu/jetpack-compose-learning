package com.narely.feedbackjourney.features.createedituser.domain

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.createedituser.data.CreateEditUserRepositoryImpl
import com.narely.feedbackjourney.features.managementuser.domain.mapper.toUserType
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.managementuser.domain.model.UserTypeEnum
import com.narely.feedbackjourney.features.managementuser.domain.GetUserUseCase
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
    private lateinit var createEditUserRepositoryImpl: CreateEditUserRepositoryImpl

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
                type = UserTypeEnum.PDM.name,
                pdmId = null,
                active = true
            )
            val item = UserDataModel(
                id = userResponse.id,
                name = userResponse.name,
                email = userResponse.email,
                type = userResponse.type.toUserType(),
                pdmEmail = null,
            )

            coEvery { createEditUserRepositoryImpl.getUser(userId) } returns userResponse

            // WHEN
            val result = getUserUseCase.invoke(id = userId, listPdm = listOf(item))

            // THEN
            Assertions.assertEquals(item, result)
        }

    @Test
    fun `GIVEN userId is null WHEN invoke() is called THEN validate that the result is null`() =
        runTest {
            // GIVEN
            val incorrectId = 2

            coEvery { createEditUserRepositoryImpl.getUser(incorrectId) } returns null

            // WHEN
            val result = getUserUseCase.invoke(incorrectId, listPdm = emptyList())

            // THEN
            Assertions.assertNull(result)
        }
}