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
    fun `GIVEN userId not null WHEN invoke() is called THEN validate result is user`() {
        // GIVEN
        val item = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )

        every { usersRepositoryImpl.getUser("23324984") } returns item

        // WHEN
        val result = getUserUseCase.invoke("23324984")

        // THEN
        Assertions.assertEquals(item, result)
    }

    @Test
    fun `GIVEN userId is null WHEN invoke() is called THEN validate result is not empty`() {
        // GIVEN
        every { usersRepositoryImpl.getUser(null) } returns null

        // WHEN
        val result = getUserUseCase.invoke(null)

        // THEN
        Assertions.assertNull(result)
    }
}