package com.narely.feedbackjourney.home.domain

import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
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
    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

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

            coJustRun { usersRepositoryImpl.removeUser(userId) }

            // WHEN
            removeUserUseCase.invoke(userId)

            // THEN
            coVerify { usersRepositoryImpl.removeUser(userId) }
        }
}