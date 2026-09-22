package com.narely.feedbackjourney.features.home.domain

import com.narely.feedbackjourney.features.managementuser.data.HomeRepositoryImpl
import com.narely.feedbackjourney.features.managementuser.domain.RemoveUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoveUserUseCaseTest {
    @MockK
    private lateinit var homeRepositoryImpl: HomeRepositoryImpl

    @InjectMockKs
    private lateinit var removeUserUseCase: RemoveUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN the userId of the user to be removed WHEN invoke() is called THEN validate that the repository's remove function is called`() =
        runTest {
            // GIVEN
            val userId = 1

            coJustRun { homeRepositoryImpl.removeUser(userId) }

            // WHEN
            removeUserUseCase.invoke(userId)

            // THEN
            coVerify { homeRepositoryImpl.removeUser(userId) }
        }
}