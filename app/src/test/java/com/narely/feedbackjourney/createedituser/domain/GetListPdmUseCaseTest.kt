package com.narely.feedbackjourney.createedituser.domain

import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import com.narely.feedbackjourney.core.model.UserResponse
import com.narely.feedbackjourney.createedituser.ui.UserTypeEnum
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class GetListPdmUseCaseTest {
    @MockK
    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    @InjectMockKs
    private lateinit var getListPdmUseCase: GetListPdmUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN an any PDM list WHEN invoke() is called THEN validate that the repository's getListPdm function is called`() =
        runTest {
            // GIVEN
            coEvery { usersRepositoryImpl.getListPdm() } returns emptyList()

            // WHEN
            getListPdmUseCase.invoke()

            // THEN
            coVerify { usersRepositoryImpl.getListPdm() }
        }

    @Test
    fun `GIVEN an empty PDM list WHEN invoke() is called THEN validate result is empty`() =
        runTest {
            // GIVEN
            coEvery { usersRepositoryImpl.getListPdm() } returns emptyList()

            // WHEN
            val result = getListPdmUseCase.invoke()

            // THEN
            Assertions.assertEquals(0, result?.size)
        }

    @Test
    fun `GIVEN a non-empty list of PDMs WHEN invoke() is called THEN validate result is non-empty`() =
        runTest {
            // GIVEN
            val userResponse = UserResponse(
                id = 1,
                name = "savi",
                email = "savi@ciandt.com",
                type = UserTypeEnum.PDM.userValue,
                pdmId = null,
                active = true
            )

            coEvery { usersRepositoryImpl.getListPdm() } returns listOf(userResponse)

            // WHEN
            val result = getListPdmUseCase.invoke()

            // THEN
            Assertions.assertEquals(1, result?.size)
            Assertions.assertEquals(listOf(userResponse.email), result)
        }
}