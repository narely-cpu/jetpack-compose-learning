package com.narely.feedbackjourney.core.domain

import com.narely.feedbackjourney.core.data.UsersRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class GetUsersUseCaseTest {

    @MockK
    private lateinit var usersRepository: UsersRepository

    @InjectMockKs
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN list is empty WHEN invoke() is called THEN validate result is empty`() {
        // GIVEN
        every { usersRepository.getUsers() } returns mutableListOf()
        // WHEN
        val result = getUsersUseCase.invoke()

        // THEN
        Assertions.assertEquals(0, result.size)
    }
}