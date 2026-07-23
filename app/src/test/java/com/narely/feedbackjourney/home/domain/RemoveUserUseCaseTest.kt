package com.narely.feedbackjourney.home.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import com.narely.feedbackjourney.createuser.domain.CreateUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class RemoveUserUseCaseTest {
    @MockK
    private lateinit var usersRepository: UsersRepository

    @InjectMockKs
    private lateinit var removeUserUseCase: RemoveUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN userId WHEN invoke() is called THEN validate result call function`() {
        // GIVEN
        val userId = "23324984"
        justRun { usersRepository.removeUser(userId) }

        // WHEN
        removeUserUseCase.invoke(userId)

        // THEN
        verify { usersRepository.removeUser(userId) }
    }
}