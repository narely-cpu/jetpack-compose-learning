package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.justRun
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.util.UUID

class CreateUserUseCaseTest {

    @RelaxedMockK
    private lateinit var usersRepository: UsersRepository

    @InjectMockKs
    private lateinit var createUserUseCase: CreateUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(UUID::class)
    }

    @Test
    fun `GIVEN added user WHEN invoke() is called THEN validate call function`() {
        // GIVEN
        val userModel = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )

        every { UUID.randomUUID().toString() } returns "23324984"
        justRun { usersRepository.createUser(userModel) }

        // WHEN
        createUserUseCase.invoke(
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )

        // THEN
        verify { usersRepository.createUser(userModel) }
    }
}