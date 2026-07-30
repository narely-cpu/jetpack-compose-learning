package com.narely.feedbackjourney.core.data

import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import io.mockk.every
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class UsersRepositoryTest {
    lateinit var usersRepository: UsersRepository
    lateinit var userModel: UserDataModel

    @Before
    fun setup() {
        usersRepository = UsersRepository()
        userModel = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )
    }

    @After
    fun tearDown() {
        usersRepository.listUser.clear()
    }

    @Test
    fun `GIVEN list is empty WHEN getUsers() is called THEN validate result is an empty list`() {
        // GIVEN
        val listUsers = emptyList<UserDataModel>()

        // WHEN
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(listUsers, result)
    }

    @Test
    fun `GIVEN list not null WHEN getUsers() is called THEN validate result`() {
        // GIVEN
        usersRepository = UsersRepository(items = listOf(userModel))

        // WHEN
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN userId is null WHEN getUser() is called THEN validate result`() {
        // GIVEN
        val userId = null

        // WHEN
        val result = usersRepository.getUser(userId)

        // THEN
        Assertions.assertNull(result)
    }

    @Test
    fun `GIVEN userId not exist WHEN getUser() is called THEN validate result`() {
        // GIVEN
        val incorrectId = "1234"

        usersRepository = UsersRepository(listOf(userModel))

        // WHEN
        val result = usersRepository.getUser(incorrectId)

        // THEN
        Assertions.assertNull(result)
    }

    @Test
    fun `GIVEN userId exists WHEN getUser() is called THEN validate result`() {
        // GIVEN
        val userId = userModel.id

        usersRepository = UsersRepository(listOf(userModel))

        // WHEN
        val result = usersRepository.getUser(userId)

        // THEN
        Assertions.assertEquals(userModel, result)
    }

    @Test
    fun `GIVEN added user to list WHEN createUser() is called THEN validate result size of list is 1`() {
        // GIVEN

        // WHEN
        usersRepository.createUser(userModel)
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN remove user to list WHEN removeUser() is called THEN validate result size of list is 0`() {
        // GIVEN
        val userId = userModel.id

        usersRepository = UsersRepository(listOf(userModel))

        // WHEN
        usersRepository.removeUser(userId)
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `GIVEN remove user not exists to list WHEN removeUser() is called THEN validate result size of list is 1`() {
        // GIVEN
        val incorrectId = "1234"

        usersRepository = UsersRepository(listOf(userModel))

        // WHEN
        usersRepository.removeUser(incorrectId)
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN edit user WHEN updateUser() is called THEN validate result user updated`() {
        // GIVEN
        usersRepository = UsersRepository(listOf(userModel))

        val updatedUser = UserDataModel(
            id = userModel.id,
            name = "savioli",
            email = userModel.email,
            password =  userModel.password,
            userType =  userModel.userType,
            pdmEmail = userModel.pdmEmail
        )

        // WHEN
        usersRepository.updateUser(
            id = updatedUser.id,
            name = updatedUser.name,
            email = updatedUser.email,
            password = updatedUser.password,
            userType = updatedUser.userType,
            pdmEmail = updatedUser.pdmEmail
        )
        val result = usersRepository.getUser(userModel.id)

        // THEN
        Assertions.assertEquals(updatedUser, result)
    }

    @Test
    fun `GIVEN pdm list WHEN getListPdm() is called THEN validate result size is 1`() {
        // GIVEN
        val secondUserModel = UserDataModel(
            id = "123456789",
            name = "lucas",
            email = "lucas@ciandt.com",
            password = "1236347",
            userType = UserType.Admin,
            pdmEmail = null
        )

        usersRepository = UsersRepository(listOf(userModel, secondUserModel))

        // WHEN
        val result = usersRepository.getListPdm()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN user pdm not exist list WHEN getListPdm() is called THEN validate result size is 0`() {
        // GIVEN
        val collaboratorUser = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.Collaborator,
            pdmEmail = null
        )

        val adminUser = UserDataModel(
            id = "123456789",
            name = "lucas",
            email = "lucas@ciandt.com",
            password = "1236347",
            userType = UserType.Admin,
            pdmEmail = null
        )

        usersRepository = UsersRepository(listOf(collaboratorUser, adminUser))

        // WHEN
        val result = usersRepository.getListPdm()

        // THEN
        Assertions.assertEquals(0, result.size)
    }
}