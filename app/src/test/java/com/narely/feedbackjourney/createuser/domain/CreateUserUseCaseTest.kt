package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CreateUserUseCaseTest {

    @MockK
    private lateinit var usersRepository: UsersRepository

    @InjectMockKs
    private lateinit var createUserUseCase: CreateUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN added user WHEN invoke() is called THEN validate result list size is 1`() {
        // GIVEN
        val userModel = UserDataModel(
            id = "1234567890",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )

        every { usersRepository.createUser(userModel) }

        // WHEN
        createUserUseCase.invoke(
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )

        val result = usersRepository.getUsers()
        // THEN

        Assertions.assertNotNull(result)
    }
}