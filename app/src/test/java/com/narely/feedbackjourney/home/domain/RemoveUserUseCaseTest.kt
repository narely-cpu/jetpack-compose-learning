package com.narely.feedbackjourney.home.domain

import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.verify
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
    fun `GIVEN userId WHEN invoke() is called THEN validate result call function`() {
        // GIVEN
        justRun { usersRepositoryImpl.removeUser("23324984") }
        // WHEN
        removeUserUseCase.invoke("23324984")
        // THEN
        verify { usersRepositoryImpl.removeUser("23324984") }
    }
}