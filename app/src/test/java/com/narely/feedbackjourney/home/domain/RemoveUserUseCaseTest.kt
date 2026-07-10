package com.narely.feedbackjourney.home.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import com.narely.feedbackjourney.createuser.domain.CreateUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*

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
    fun `GIVEN userId exists WHEN invoke() is called THEN validate result list size is 0`() {
        // GIVEN
        // preciso criar um user com o id "23324984"
        every { usersRepository.removeUser("23324984") }

        // WHEN
        removeUserUseCase.invoke("23324984")
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(0, result.size)
    }
}