package com.narely.feedbackjourney.core.data

import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import org.junit.Test
import org.junit.jupiter.api.Assertions

class UsersRepositoryTest {
    lateinit var usersRepository: UsersRepository

    @Test
    fun `GIVEN list is empty WHEN getUsers() is called THEN validate result`() {
        // GIVEN
        usersRepository = UsersRepository()

        // WHEN
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `GIVEN list not null WHEN getUsers() is called THEN validate result`() {
        // GIVEN
        val item = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )
        usersRepository = UsersRepository(items = listOf(item))

        // WHEN
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN userId is null WHEN getUser() is called THEN validate result`() {
        // GIVEN
        usersRepository = UsersRepository()

        // WHEN
        val result = usersRepository.getUser(null)

        // THEN
        Assertions.assertNull(result)
    }

    @Test
    fun `GIVEN userId not exist WHEN getUser() is called THEN validate result`() {
        // GIVEN
        val item = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )
        usersRepository = UsersRepository(listOf(item))

        // WHEN
        val result = usersRepository.getUser("1234")

        // THEN
        Assertions.assertNull(result)
    }

    @Test
    fun `GIVEN userId exists WHEN getUser() is called THEN validate result`() {
        // GIVEN
        val item = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )
        usersRepository = UsersRepository(listOf(item))

        // WHEN
        val result = usersRepository.getUser("23324984")

        // THEN
        Assertions.assertEquals(item, result)
    }

    @Test
    fun `GIVEN added user to list WHEN createUser() is called THEN validate result size of list is 1`() {
        // GIVEN
        val userModel = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )
        usersRepository = UsersRepository()

        // WHEN
        usersRepository.createUser(userModel)
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN remove user to list WHEN removeUser() is called THEN validate result size of list is 0`() {
        // GIVEN
        val item = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )
        usersRepository = UsersRepository(listOf(item))

        // WHEN
        usersRepository.removeUser("23324984")
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `GIVEN remove user not exists to list WHEN removeUser() is called THEN validate result size of list is 1`() {
        // GIVEN
        val item = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null,
        )
        usersRepository = UsersRepository(listOf(item))

        // WHEN
        usersRepository.removeUser("123")
        val result = usersRepository.getUsers()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN edit user WHEN updateUser() is called THEN validate result user updated`() {
        // GIVEN
        val item = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null
        )

        usersRepository = UsersRepository(listOf(item))

        val updatedUser = UserDataModel(
            id = "23324984",
            name = "savioli",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null
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
        val result = usersRepository.getUser("23324984")

        // THEN
        Assertions.assertEquals(updatedUser, result)
    }

    @Test
    fun `GIVEN pdm list WHEN getListPdm() is called THEN validate result size is 1`() {
        // GIVEN
        val firstItem = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.PDM,
            pdmEmail = null
        )

        val secondItem = UserDataModel(
            id = "123456789",
            name = "lucas",
            email = "lucas@ciandt.com",
            password = "1236347",
            userType = UserType.Admin,
            pdmEmail = null
        )

        usersRepository = UsersRepository(listOf(firstItem, secondItem))

        // WHEN
        val result = usersRepository.getListPdm()

        // THEN
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `GIVEN user pdm not exist list WHEN getListPdm() is called THEN validate result size is 0`() {
        // GIVEN
        val firstItem = UserDataModel(
            id = "23324984",
            name = "savi",
            email = "savi@ciandt.com",
            password = "1236347",
            userType = UserType.Collaborator,
            pdmEmail = null
        )

        val secondItem = UserDataModel(
            id = "123456789",
            name = "lucas",
            email = "lucas@ciandt.com",
            password = "1236347",
            userType = UserType.Admin,
            pdmEmail = null
        )

        usersRepository = UsersRepository(listOf(firstItem, secondItem))

        // WHEN
        val result = usersRepository.getListPdm()

        // THEN
        Assertions.assertEquals(0, result.size)
    }
}