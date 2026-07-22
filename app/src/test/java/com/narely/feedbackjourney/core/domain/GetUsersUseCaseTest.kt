package com.narely.feedbackjourney.core.domain

import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class GetUsersUseCaseTest {

    @MockK
    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    @InjectMockKs
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN list is empty WHEN invoke() is called THEN validate result is empty`() {
        // GIVEN
        every { usersRepositoryImpl.getUsers() } returns mutableListOf()

        // WHEN
        val result = getUsersUseCase.invoke()

        // THEN
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `GIVEN list is not empty WHEN invoke() is called THEN validate result is not empty`() {
        // GIVEN
        val item = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )

        every { usersRepositoryImpl.getUsers() } returns mutableListOf(item)

        // WHEN
        val result = getUsersUseCase.invoke()

        // THEN
        Assertions.assertEquals(1, result.size)
    }
}