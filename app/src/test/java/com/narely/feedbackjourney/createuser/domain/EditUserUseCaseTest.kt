package com.narely.feedbackjourney.createuser.domain

import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import com.narely.feedbackjourney.core.model.UserType
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class EditUserUseCaseTest {

    @MockK
    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    @InjectMockKs
    private lateinit var editUserUseCase: EditUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN edit userId WHEN invoke() is called THEN validate call edit function`() {
        //GIVEN
        justRun { usersRepositoryImpl.updateUser(
            id = "1234567890",
            name = "saviolli",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null
        )}

        //WHEN
        editUserUseCase.invoke(
            id = "1234567890",
            name = "saviolli",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = "PDM",
            pdmEmail = null
        )

        //THEN
        verify { usersRepositoryImpl.updateUser(id = "1234567890",
            name = "saviolli",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null
        )}

    }

}