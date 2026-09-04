package com.narely.feedbackjourney.features.home.data

import com.narely.feedbackjourney.commons.data.remote.model.UserResponse
import com.narely.feedbackjourney.commons.data.remote.model.UsersListResponse
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.managementuser.domain.model.UserTypeEnum
import com.narely.feedbackjourney.features.managementuser.data.HomeRepositoryImpl
import com.narely.feedbackjourney.features.managementuser.data.remote.HomeApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import retrofit2.Response

class HomeRepositoryTest {

    lateinit var homeRepositoryImpl: HomeRepositoryImpl

    val homeApi: HomeApi = mockk()
    val userModel: UserDataModel = UserDataModel(
        id = 1,
        name = "savi",
        email = "savi@ciandt.com",
        type = UserTypeEnum.PDM,
        pdmEmail = null,
    )

    @Before
    fun setup() {
        homeRepositoryImpl = HomeRepositoryImpl(homeApi = homeApi)
    }

    @Test
    fun `GIVEN an empty list WHEN getUsers() is called THEN validate that the size of the result is 1`() =
        runTest {
            // GIVEN
            coEvery { homeApi.getUsers() } returns UsersListResponse()

            // WHEN
            val result = homeRepositoryImpl.getUsers()

            // THEN
            Assertions.assertEquals(emptyList<UserResponse>(), result)
            coVerify { homeApi.getUsers() }
        }

    @Test
    fun `GIVEN a non-empty list WHEN getUsers() is called THEN validate that the size of the result is 1`() =
        runTest {
            // GIVEN
            val userResponse = UserResponse(
                id = userModel.id,
                name = userModel.name,
                email = userModel.email,
                type = userModel.type.name,
                pdmId = null,
                active = true
            )
            val listUser = listOf(userResponse)

            coEvery { homeApi.getUsers() } returns UsersListResponse(listUsers = listUser)

            // WHEN
            val result = homeRepositoryImpl.getUsers()

            // THEN
            Assertions.assertEquals(1, result.size)
            coVerify { homeApi.getUsers() }
        }

    @Test
    fun `GIVEN the user is removed WHEN removeUser() is called THEN validate that the list size is 0`() =
        runTest {
            // GIVEN
            val userId = userModel.id
            val userResponse = UserResponse(
                id = userModel.id,
                name = userModel.name,
                email = userModel.email,
                type = userModel.type.name,
                pdmId = null,
                active = true
            )
            val listUser = mutableListOf(userResponse)

            coEvery { homeApi.removeUser(userId) } coAnswers {
                listUser.remove(userResponse)
                Response.success(Unit)
            }

            // WHEN
            homeRepositoryImpl.removeUser(userId)

            // THEN
            Assertions.assertEquals(0, listUser.size)
            coVerify { homeApi.removeUser(userId) }
        }

    @Test
    fun `GIVEN a non-existent user in the list is removed WHEN removeUser() is called THEN validate that the list size is 1`() =
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
            val listUser = mutableListOf(userResponse)

            coEvery { homeApi.removeUser(incorrectId) } coAnswers {
                val user = listUser.find { it.id == incorrectId }
                listUser.remove(user)
                Response.success(Unit)
            }

            // WHEN
            homeRepositoryImpl.removeUser(incorrectId)

            // THEN
            Assertions.assertEquals(1, listUser.size)
            coVerify { homeApi.removeUser(incorrectId) }
        }
}