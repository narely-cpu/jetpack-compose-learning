package com.narely.feedbackjourney.home

import com.narely.feedbackjourney.core.domain.GetUsersUseCase
import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import com.narely.feedbackjourney.createuser.CreateEditUserViewState
import com.narely.feedbackjourney.home.domain.RemoveUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
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
    fun `GIVEN list updated WHEN updateList() is called THEN validate update list`() = runTest() {
        // GIVEN
        val userFirst = UserDataModel(id = "111232232",
            name = "New name",
            email = "New email First",
            password = "New password",
            userType = UserType.PDM,
            pdmEmail = null
        )

        val userSecond = UserDataModel(id = "111232232",
            name = "New name",
            email = "New email Second",
            password = "New password",
            userType = UserType.PDM,
            pdmEmail = null
        )

        val listUsers = mutableListOf<UserDataModel>()

        listUsers.add(userFirst)
        listUsers.add(userSecond)

        every { getUsersUseCase.invoke() } returns listUsers

        // WHEN
        homeViewModel.updateList()

        advanceUntilIdle()

        val currentUiStateAfter = homeViewModel.uiState.value

        // THEN
        verify { getUsersUseCase.invoke() }

        Assertions.assertEquals(listUsers, currentUiStateAfter.list)
    }

    @Test
    fun `GIVEN current user was changed WHEN updateCurrentUser() is called THEN validate user was changed`() {
        // GIVEN
        val newCurrentUser = UserDataModel(
            id = "111232232",
            name = "New name",
            email = "New email",
            password = "New password",
            userType = UserType.PDM,
            pdmEmail = null
        )

        // WHEN
        homeViewModel.updateCurrentUser(newCurrentUser)

        val currentUiState = homeViewModel.uiState.value

        // THEN
        Assertions.assertEquals(newCurrentUser, currentUiState.currentUser)
    }

    @Test
    fun `GIVEN user was deleted WHEN deleteUser() is called THEN validate state change`() {
        // GIVEN
        val currentUserId = "111232232"

        justRun { removeUserUseCase.invoke(currentUserId) }

        // WHEN
        homeViewModel.deleteUser(currentUserId)

        // THEN
        verify { removeUserUseCase.invoke(currentUserId) }
    }
}