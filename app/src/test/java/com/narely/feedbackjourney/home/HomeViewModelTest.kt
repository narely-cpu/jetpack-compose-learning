package com.narely.feedbackjourney.home

import com.narely.feedbackjourney.features.home.domain.GetUsersUseCase
import com.narely.feedbackjourney.core.data.remote.model.UserResponse
import com.narely.feedbackjourney.features.createedituser.domain.model.UserTypeEnum
import com.narely.feedbackjourney.features.home.domain.RemoveUserUseCase
import com.narely.feedbackjourney.features.home.ui.HomeViewModel
import com.narely.feedbackjourney.features.home.ui.HomeViewState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var getUsersUseCase: GetUsersUseCase

    @MockK
    private lateinit var removeUserUseCase: RemoveUserUseCase

    @InjectMockKs
    private lateinit var homeViewModel: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN any state changed WHEN updateUiState() is called THEN validate state change`() {
        // GIVEN
        val newState = HomeViewState(
            list = mutableListOf(),
            isLoading = false,
            currentUser = null
        )

        // WHEN
        homeViewModel.updateUiState(newState)

        val currentUiState = homeViewModel.uiState.value

        // THEN
        Assertions.assertEquals(newState, currentUiState)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN the updated list of users WHEN updateList() is called THEN validate update list`() =
        runTest() {
            // GIVEN
            val currentUiStateBefore = homeViewModel.uiState.value
            val userFirst  = UserResponse(
                id = 1,
                name = "New name",
                email = "New email First",
                type = UserTypeEnum.PDM.userValue,
                pdmId = null,
                active = true
            )
            val userSecond = UserResponse(
                id = 2,
                name = "New name",
                email = "New email Second",
                type = UserTypeEnum.PDM.userValue,
                pdmId = null,
                active = true
            )
            val listUsers = listOf(userFirst, userSecond)

            coEvery { getUsersUseCase.invoke() } returns listUsers

            // WHEN
            homeViewModel.updateList()
            advanceUntilIdle()

            val currentUiStateAfter = homeViewModel.uiState.value

            // THEN
            Assertions.assertEquals(emptyList<UserResponse>(), currentUiStateBefore.list)
            Assertions.assertEquals(listUsers, currentUiStateAfter.list)
            coVerify { getUsersUseCase.invoke() }
        }

    @Test
    fun `GIVEN current user is updated WHEN updateCurrentUser() is called THEN validate the returned user`() {
        // GIVEN
        val userResponse = UserResponse(
            id = 1,
            name = "New name",
            email = "New email",
            type = UserTypeEnum.PDM.userValue,
            pdmId = null,
            active = true
        )

        // WHEN
        homeViewModel.updateCurrentUser(userResponse)

        val currentUiState = homeViewModel.uiState.value

        // THEN
        Assertions.assertEquals(userResponse, currentUiState.currentUser)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN the userId to be deleted WHEN deleteUser() is called THEN validate the invoke() function was called`() =
        runTest {
            // GIVEN
            val currentUserId = 2

            coJustRun { removeUserUseCase.invoke(currentUserId) }

            // WHEN
            homeViewModel.removeUser(currentUserId)
            advanceUntilIdle()

            // THEN
            coVerify { removeUserUseCase.invoke(currentUserId) }
        }
}