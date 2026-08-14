package com.narely.feedbackjourney.core.data

import com.narely.feedbackjourney.createedituser.data.remote.model.CreateUserRequest
import com.narely.feedbackjourney.features.createedituser.domain.model.UserDataModel
import com.narely.feedbackjourney.core.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.createedituser.domain.model.UserTypeEnum
import com.narely.feedbackjourney.core.data.remote.model.UsersListResponse
import com.narely.feedbackjourney.core.services.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import retrofit2.Response

class UsersRepositoryImplTest {
//    lateinit var usersRepositoryImpl: UsersRepositoryImpl
//    val apiService: ApiService = mockk()
//    val userModel: UserDataModel = UserDataModel(
//        id = 1,
//        name = "savi",
//        email = "savi@ciandt.com",
//        password = "1236347",
//        type = UserTypeEnum.PDM.userValue,
//        pdmEmail = null,
//    )
//
//    @Before
//    fun setup() {
//        usersRepositoryImpl = UsersRepositoryImpl(apiService = apiService)
//    }

//    @Test
//    fun `GIVEN an empty list WHEN getUsers() is called THEN validate that the size of the result is 1`() =
//        runTest {
//            // GIVEN
//            coEvery { apiService.getUsers() } returns UsersListResponse()
//
//            // WHEN
//            val result = usersRepositoryImpl.getUsers()
//
//            // THEN
//            Assertions.assertEquals(emptyList<UserResponse>(), result)
//            coVerify { apiService.getUsers() }
//        }
//
//    @Test
//    fun `GIVEN a non-empty list WHEN getUsers() is called THEN validate that the size of the result is 1`() =
//        runTest {
//            // GIVEN
//            val userResponse = UserResponse(
//                id = userModel.id,
//                name = userModel.name,
//                email = userModel.email,
//                type = userModel.type,
//                pdmId = null,
//                active = true
//            )
//            val listUser = listOf(userResponse)
//
//            coEvery { apiService.getUsers() } returns UsersListResponse(content = listUser)
//
//            // WHEN
//            val result = usersRepositoryImpl.getUsers()
//
//            // THEN
//            Assertions.assertEquals(1, result.size)
//            coVerify { apiService.getUsers() }
//        }

//    @Test
//    fun `GIVEN a userId corresponding to an non-existent user in the list WHEN getUser() is called THEN that the returned value is null`() =
//        runTest {
//            // GIVEN
//            val incorrectId = 2
//            val userResponse = UserResponse(
//                id = userModel.id,
//                name = userModel.name,
//                email = userModel.email,
//                type = userModel.type,
//                pdmId = null,
//                active = true
//            )
//            val listUser = listOf(userResponse)
//            var user: UserResponse? = null
//
//            coEvery { apiService.getUser(incorrectId) } coAnswers {
//                user = listUser.find { it.id == incorrectId }
//                user
//            }
//
//            // WHEN
//            usersRepositoryImpl.getUser(incorrectId)
//
//            // THEN
//            Assertions.assertNull(user)
//            coVerify { apiService.getUser(incorrectId) }
//        }

//    @Test
//    fun `GIVEN a userId corresponding to an existing user in the list WHEN getUser() is called THEN that the returned value is that user`() =
//        runTest {
//            // GIVEN
//            val correctId = userModel.id
//            val userResponse = UserResponse(
//                id = userModel.id,
//                name = userModel.name,
//                email = userModel.email,
//                type = userModel.type,
//                pdmId = null,
//                active = true
//            )
//            val listUser = listOf(userResponse)
//            var user: UserResponse? = null
//
//            coEvery { apiService.getUser(correctId) } coAnswers {
//                user = listUser.find { it.id == correctId }
//                user
//            }
//
//            // WHEN
//            usersRepositoryImpl.getUser(correctId)
//
//            // THEN
//            Assertions.assertEquals(userResponse, user)
//            coVerify { apiService.getUser(correctId) }
//        }

//    @Test
//    fun `GIVEN a user added to the list WHEN createUser() is called THEN validate that the list size is 1`() =
//        runTest {
//            // GIVEN
//            val request = CreateUserRequest(
//                name = userModel.name,
//                email = userModel.email,
//                type = userModel.type,
//                pdmId = null
//            )
//            val userResponse = UserResponse(
//                id = userModel.id,
//                name = userModel.name,
//                email = userModel.email,
//                type = userModel.type,
//                pdmId = null,
//                active = true
//            )
//            val listUser = mutableListOf<UserResponse>()
//
//            coEvery { apiService.createUser(request) } coAnswers {
//                listUser.add(userResponse)
//                userResponse
//            }
//
//            // WHEN
//            usersRepositoryImpl.createUser(request = request)
//
//            // THEN
//            Assertions.assertEquals(1, listUser.size)
//            coVerify { apiService.createUser(request) }
//        }

//    @Test
//    fun `GIVEN the user is removed WHEN removeUser() is called THEN validate that the list size is 0`() =
//        runTest {
//            // GIVEN
//            val userId = userModel.id
//            val userResponse = UserResponse(
//                id = userModel.id,
//                name = userModel.name,
//                email = userModel.email,
//                type = userModel.type,
//                pdmId = null,
//                active = true
//            )
//            val listUser = mutableListOf(userResponse)
//
//            coEvery { apiService.removeUser(userId) } coAnswers {
//                listUser.remove(userResponse)
//                Response.success(Unit)
//            }
//
//            // WHEN
//            usersRepositoryImpl.removeUser(userId)
//
//            // THEN
//            Assertions.assertEquals(0, listUser.size)
//            coVerify { apiService.removeUser(userId) }
//        }
//
//    @Test
//    fun `GIVEN a non-existent user in the list is removed WHEN removeUser() is called THEN validate that the list size is 1`() =
//        runTest {
//            // GIVEN
//            val incorrectId = 2
//            val userResponse = UserResponse(
//                id = userModel.id,
//                name = userModel.name,
//                email = userModel.email,
//                type = userModel.type,
//                pdmId = null,
//                active = true
//            )
//            val listUser = mutableListOf(userResponse)
//
//            coEvery { apiService.removeUser(incorrectId) } coAnswers {
//                val user = listUser.find { it.id == incorrectId }
//                listUser.remove(user)
//                Response.success(Unit)
//            }
//
//            // WHEN
//            usersRepositoryImpl.removeUser(incorrectId)
//
//            // THEN
//            Assertions.assertEquals(1, listUser.size)
//            coVerify { apiService.removeUser(incorrectId) }
//        }

//    @Test
//    fun `GIVEN an updated user WHEN updateUser() is called THEN validate that the return value is the updated user`() =
//        runTest {
//            // GIVEN
//            var userResponse = UserResponse(
//                id = userModel.id,
//                name = userModel.name,
//                email = userModel.email,
//                type = userModel.type,
//                pdmId = null,
//                active = true
//            )
//            val request = UpdateUserRequest(
//                name = "saviolli",
//                email = userModel.email,
//                type = userModel.type,
//                pdmId = null
//            )
//            val updatedUser = UserResponse(
//                id = userModel.id,
//                name = "saviolli",
//                email = request.email,
//                type = request.type,
//                pdmId = null,
//                active = true
//            )
//            coEvery { apiService.updateUser(userModel.id, request) } coAnswers {
//                userResponse = updatedUser
//                userResponse
//            }
//
//            // WHEN
//            usersRepositoryImpl.updateUser(id = userModel.id, request = request)
//
//            // THEN
//            Assertions.assertEquals(updatedUser, userResponse)
//            coVerify { apiService.updateUser(userModel.id, request) }
//        }
//
//    @Test
//    fun `GIVEN a list of PDM users WHEN getListPdm() is called THEN validate that the size is 1`() =
//        runTest {
//            // GIVEN
//            val firstUserResponse = UserResponse(
//                id = userModel.id,
//                name = userModel.name,
//                email = userModel.email,
//                type = userModel.type,
//                pdmId = null,
//                active = true
//            )
//            val secondUserResponse = UserResponse(
//                id = 2,
//                name = "lucas",
//                email = "lucas@ciandt.com",
//                type = UserTypeEnum.ADMIN.userValue,
//                pdmId = null,
//                active = true
//            )
//            var listUser = listOf(firstUserResponse, secondUserResponse)
//
//            coEvery { apiService.getListPdm() } coAnswers {
//                listUser = listUser.filter { it.type == "PDM" }
//                UsersListResponse(content = listUser)
//            }
//
//            // WHEN
//            usersRepositoryImpl.getListPdm()
//
//            // THEN
//            Assertions.assertEquals(1, listUser.size)
//            coVerify { apiService.getListPdm() }
//        }
//
//    @Test
//    fun `GIVEN a list without users of type PMM WHEN getListPdm() is called THEN validate that the size is 1`() =
//        runTest {
//            // GIVEN
//            val collaboratorUserResponse = UserResponse(
//                id = 3,
//                name = "savi",
//                email = "savi@ciandt.com",
//                type = UserTypeEnum.COLLABORATOR.userValue,
//                pdmId = 1,
//                active = true
//            )
//            val adminUserResponse = UserResponse(
//                id = 4,
//                name = "lucas",
//                email = "lucas@ciandt.com",
//                type = UserTypeEnum.ADMIN.userValue,
//                pdmId = null,
//                active = true
//            )
//            var listUser = listOf(collaboratorUserResponse, adminUserResponse)
//
//            coEvery { apiService.getListPdm() } coAnswers {
//                listUser = listUser.filter { it.type == "PDM" }
//                UsersListResponse(content = listUser)
//            }
//
//            // WHEN
//            usersRepositoryImpl.getListPdm()
//
//            // THEN
//            Assertions.assertEquals(0, listUser.size)
//        }
}