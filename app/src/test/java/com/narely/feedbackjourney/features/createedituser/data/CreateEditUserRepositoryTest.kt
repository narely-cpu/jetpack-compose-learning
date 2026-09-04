package com.narely.feedbackjourney.features.createedituser.data

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.commons.data.remote.model.UsersListResponse
import com.narely.feedbackjourney.features.createedituser.data.remote.CreateEditUserApi
import com.narely.feedbackjourney.features.managementuser.data.remote.model.CreateEditUserRequest
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.managementuser.domain.model.UserTypeEnum
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class CreateEditUserRepositoryTest {
    lateinit var createEditUserRepositoryImpl: CreateEditUserRepositoryImpl
    val createEditUserApi: CreateEditUserApi = mockk()
    val userModel: UserDataModel = UserDataModel(
        id = 1,
        name = "savi",
        email = "savi@ciandt.com",
        type = UserTypeEnum.PDM,
        pdmEmail = null,
    )

    @Before
    fun setup() {
        createEditUserRepositoryImpl = CreateEditUserRepositoryImpl(createEditUserApi = createEditUserApi)
    }

    @Test
    fun `GIVEN a userId corresponding to an non-existent user in the list WHEN getUser() is called THEN that the returned value is null`() =
        runTest {
            // GIVEN
            val incorrectId = 2
            val userResponse = UserResponse(
                id = userModel.id,
                name = userModel.name,
                email = userModel.email,
                type = userModel.type.name,
                pdmId = null,
                active = true
            )
            val listUser = listOf(userResponse)
            var user: UserResponse? = null

            coEvery { createEditUserApi.getUser(incorrectId) } coAnswers {
                user = listUser.find { it.id == incorrectId }
                user
            }

            // WHEN
            createEditUserRepositoryImpl.getUser(incorrectId)

            // THEN
            Assertions.assertNull(user)
            coVerify { createEditUserApi.getUser(incorrectId) }
        }

    @Test
    fun `GIVEN a userId corresponding to an existing user in the list WHEN getUser() is called THEN that the returned value is that user`() =
        runTest {
            // GIVEN
            val correctId = userModel.id
            val userResponse = UserResponse(
                id = userModel.id,
                name = userModel.name,
                email = userModel.email,
                type = userModel.type.name,
                pdmId = null,
                active = true
            )
            val listUser = listOf(userResponse)
            var user: UserResponse? = null

            coEvery { createEditUserApi.getUser(correctId) } coAnswers {
                user = listUser.find { it.id == correctId }
                user
            }

            // WHEN
            createEditUserRepositoryImpl.getUser(correctId)

            // THEN
            Assertions.assertEquals(userResponse, user)
            coVerify { createEditUserApi.getUser(correctId) }
        }

    @Test
    fun `GIVEN a user added to the list WHEN createUser() is called THEN validate that the list size is 1`() =
        runTest {
            // GIVEN
            val request = CreateEditUserRequest(
                name = userModel.name,
                email = userModel.email,
                type = userModel.type.name,
                pdmId = null
            )
            val userResponse = UserResponse(
                id = userModel.id,
                name = userModel.name,
                email = userModel.email,
                type = userModel.type.name,
                pdmId = null,
                active = true
            )
            val listUser = mutableListOf<UserResponse>()

            coEvery { createEditUserApi.createUser(request) } coAnswers {
                listUser.add(userResponse)
                userResponse
            }

            // WHEN
            createEditUserRepositoryImpl.createUser(request = request)

            // THEN
            Assertions.assertEquals(1, listUser.size)
            coVerify { createEditUserApi.createUser(request) }
        }

    @Test
    fun `GIVEN an updated user WHEN updateUser() is called THEN validate that the return value is the updated user`() =
        runTest {
            // GIVEN
            var userResponse = UserResponse(
                id = userModel.id,
                name = userModel.name,
                email = userModel.email,
                type = userModel.type.name,
                pdmId = null,
                active = true
            )
            val request = CreateEditUserRequest(
                name = "saviolli",
                email = userModel.email,
                type = userModel.type.name,
                pdmId = null
            )
            val updatedUser = UserResponse(
                id = userModel.id,
                name = "saviolli",
                email = request.email,
                type = request.type,
                pdmId = null,
                active = true
            )
            coEvery { createEditUserApi.updateUser(userModel.id, request) } coAnswers {
                userResponse = updatedUser
                userResponse
            }

            // WHEN
            createEditUserRepositoryImpl.updateUser(id = userModel.id, request = request)

            // THEN
            Assertions.assertEquals(updatedUser, userResponse)
            coVerify { createEditUserApi.updateUser(userModel.id, request) }
        }

    @Test
    fun `GIVEN a list of PDM users WHEN getListPdm() is called THEN validate that the size is 1`() =
        runTest {
            // GIVEN
            val firstUserResponse = UserResponse(
                id = userModel.id,
                name = userModel.name,
                email = userModel.email,
                type = userModel.type.name,
                pdmId = null,
                active = true
            )
            val secondUserResponse = UserResponse(
                id = 2,
                name = "lucas",
                email = "lucas@ciandt.com",
                type = UserTypeEnum.ADMIN.name,
                pdmId = null,
                active = true
            )
            var listUser = listOf(firstUserResponse, secondUserResponse)

            coEvery { createEditUserApi.getListPdm() } coAnswers {
                listUser = listUser.filter { it.type == "PDM" }
                UsersListResponse(listUsers = listUser)
            }

            // WHEN
            createEditUserRepositoryImpl.getListPdm()

            // THEN
            Assertions.assertEquals(1, listUser.size)
            coVerify { createEditUserApi.getListPdm() }
        }

    @Test
    fun `GIVEN a list without users of type PMM WHEN getListPdm() is called THEN validate that the size is 1`() =
        runTest {
            // GIVEN
            val collaboratorUserResponse = UserResponse(
                id = 3,
                name = "savi",
                email = "savi@ciandt.com",
                type = UserTypeEnum.COLLABORATOR.name,
                pdmId = 1,
                active = true
            )
            val adminUserResponse = UserResponse(
                id = 4,
                name = "lucas",
                email = "lucas@ciandt.com",
                type = UserTypeEnum.ADMIN.name,
                pdmId = null,
                active = true
            )
            var listUser = listOf(collaboratorUserResponse, adminUserResponse)

            coEvery { createEditUserApi.getListPdm() } coAnswers {
                listUser = listUser.filter { it.type == "PDM" }
                UsersListResponse(listUsers = listUser)
            }

            // WHEN
            createEditUserRepositoryImpl.getListPdm()

            // THEN
            Assertions.assertEquals(0, listUser.size)
        }
}