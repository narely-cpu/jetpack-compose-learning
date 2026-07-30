package com.narely.feedbackjourney.core.data

import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class UsersRepositoryImplTest {
    lateinit var usersRepositoryImpl: UsersRepositoryImpl
    lateinit var userModel: UserDataModel

    @Before
    fun setup() {
        usersRepositoryImpl = UsersRepositoryImpl()
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
        usersRepositoryImpl.listUser.clear()
    }

    @Test
    fun `GIVEN list is empty WHEN getUsers() is called THEN validate result is an empty list`() {
        // GIVEN
        val listUsers = emptyList<UserDataModel>()

        // WHEN
        val result = usersRepositoryImpl.getUsers()

        // THEN
        Assertions.assertEquals(listUsers, result)
    }

    @Test
    fun `GIVEN list not null WHEN getUsers() is called THEN validate result`() {
        // GIVEN
        usersRepositoryImpl = UsersRepositoryImpl(items = listOf(userModel))

        // WHEN
        val result = usersRepositoryImpl.getUsers()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN userId is null WHEN getUser() is called THEN validate result`() {
        // GIVEN
        val userId = null

        // WHEN
        val result = usersRepositoryImpl.getUser(userId)

        // THEN
        Assertions.assertNull(result)
    }

    @Test
    fun `GIVEN userId not exist WHEN getUser() is called THEN validate result`() {
        // GIVEN

        val incorrectId = "1234"

        usersRepositoryImpl = UsersRepositoryImpl(listOf(userModel))

        // WHEN
        val result = usersRepositoryImpl.getUser(incorrectId)

        // THEN
        Assertions.assertNull(result)
    }

    @Test
    fun `GIVEN userId exists WHEN getUser() is called THEN validate result`() {
        // GIVEN
        val userId = userModel.id

        usersRepositoryImpl = UsersRepositoryImpl(listOf(userModel))

        // WHEN
        val result = usersRepositoryImpl.getUser(userId)

        // THEN
        Assertions.assertEquals(userModel, result)
    }

    @Test
    fun `GIVEN added user to list WHEN createUser() is called THEN validate result size of list is 1`() {
        // GIVEN

        // WHEN
        usersRepositoryImpl.createUser(userModel)
        val result = usersRepositoryImpl.getUsers()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN remove user to list WHEN removeUser() is called THEN validate result size of list is 0`() {
        // GIVEN
        val userId = userModel.id

        usersRepositoryImpl = UsersRepositoryImpl(listOf(userModel))

        // WHEN
        usersRepositoryImpl.removeUser(userId)
        val result = usersRepositoryImpl.getUsers()

        // THEN
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `GIVEN remove user not exists to list WHEN removeUser() is called THEN validate result size of list is 1`() {
        // GIVEN
        val incorrectId = "1234"

        usersRepositoryImpl = UsersRepositoryImpl(listOf(userModel))

        // WHEN
        usersRepositoryImpl.removeUser(incorrectId)
        val result = usersRepositoryImpl.getUsers()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN edit user WHEN updateUser() is called THEN validate result user updated`() {
        // GIVEN
        usersRepositoryImpl = UsersRepositoryImpl(listOf(userModel))

        val updatedUser = UserDataModel(
            id = userModel.id,
            name = "savioli",
            email = userModel.email,
            password =  userModel.password,
            userType =  userModel.userType,
            pdmEmail = userModel.pdmEmail
        )

        // WHEN
        usersRepositoryImpl.updateUser(
            id = updatedUser.id,
            name = updatedUser.name,
            email = updatedUser.email,
            password = updatedUser.password,
            userType = updatedUser.userType,
            pdmEmail = updatedUser.pdmEmail
        )
        val result = usersRepositoryImpl.getUser(userModel.id)

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

        usersRepositoryImpl = UsersRepositoryImpl(listOf(userModel, secondUserModel))

        // WHEN
        val result = usersRepositoryImpl.getListPdm()

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

        usersRepositoryImpl = UsersRepositoryImpl(listOf(collaboratorUser, adminUser))

        // WHEN
        val result = usersRepositoryImpl.getListPdm()

        // THEN
        Assertions.assertEquals(0, result.size)
    }
}