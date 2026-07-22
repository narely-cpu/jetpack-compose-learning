package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class GetListPdmUseCaseTest {
    @MockK
    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    @InjectMockKs
    private lateinit var getListPdmUseCase: GetListPdmUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN list is empty WHEN invoke() is called THEN validate result is empty`() {
        // GIVEN
        every { usersRepositoryImpl.getListPdm() } returns mutableListOf()

        // WHEN
        val result = getListPdmUseCase.invoke()

        // THEN
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `GIVEN list is not empty WHEN invoke() is called THEN validate result is not empty`() {
        // GIVEN
        val email = "savi@ciandt.com"

        every { usersRepositoryImpl.getListPdm() } returns listOf(email)

        // WHEN
        val result = getListPdmUseCase.invoke()

        // THEN
        Assertions.assertEquals(1, result.size)
    }
}