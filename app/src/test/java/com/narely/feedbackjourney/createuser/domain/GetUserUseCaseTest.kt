package com.narely.feedbackjourney.createuser.domain

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

class GetUserUseCaseTest {
    @MockK
    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    @InjectMockKs
    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN userId not null WHEN invoke() is called THEN validate correct user is returned`() {
        // GIVEN
        val userId = "23324984"
        val item = UserDataModel(
            id = userId,
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )

        every { usersRepositoryImpl.getUser(userId) } returns item

        // WHEN
        val result = getUserUseCase.invoke(userId)

        // THEN
        Assertions.assertEquals(item, result)
    }

    @Test
    fun `GIVEN userId is null WHEN invoke() is called THEN validate result is null`() {
        // GIVEN
        every { usersRepositoryImpl.getUser(null) } returns null

        // WHEN
        val result = getUserUseCase.invoke(null)

        // THEN
        Assertions.assertNull(result)
    }
}