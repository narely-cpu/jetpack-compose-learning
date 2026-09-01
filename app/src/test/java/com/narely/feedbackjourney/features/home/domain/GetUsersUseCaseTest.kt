package com.narely.feedbackjourney.features.home.domain

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.createedituser.domain.model.UserTypeEnum
import com.narely.feedbackjourney.features.managementuser.data.HomeRepositoryImpl
import com.narely.feedbackjourney.features.managementuser.domain.GetUsersUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class GetUsersUseCaseTest {
    @MockK
    private lateinit var homeRepositoryImpl: HomeRepositoryImpl

    @InjectMockKs
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN list is empty WHEN invoke() is called THEN validate result is empty`() =
        runTest {
            // GIVEN
            coEvery { homeRepositoryImpl.getUsers() } returns listOf()

            // WHEN
            val result = getUsersUseCase.invoke()

            // THEN
            Assertions.assertEquals(0, result.size)
        }

    @Test
    fun `GIVEN list is not empty WHEN invoke() is called THEN validate result is not empty`() =
        runTest {
            // GIVEN
            val userResponse = UserResponse(
                id = 1,
                name = "savi",
                email = "savi@ciandt.com",
                type = UserTypeEnum.PDM.name,
                pdmId = null,
                active = true
            )
            val listUsers = listOf(userResponse)

            coEvery { homeRepositoryImpl.getUsers() } returns listUsers

            // WHEN
            val result = getUsersUseCase.invoke()

            // THEN
            Assertions.assertEquals(1, result.size)
        }
}