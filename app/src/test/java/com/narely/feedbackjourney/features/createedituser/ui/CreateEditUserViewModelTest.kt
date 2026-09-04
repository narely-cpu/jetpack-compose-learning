package com.narely.feedbackjourney.features.createedituser.ui

import com.narely.feedbackjourney.features.managementuser.domain.CreateUserUseCase
import com.narely.feedbackjourney.features.managementuser.domain.EditUserUseCase
import com.narely.feedbackjourney.features.managementuser.domain.GetListPdmUseCase
import com.narely.feedbackjourney.features.managementuser.domain.GetUserUseCase
import com.narely.feedbackjourney.features.managementuser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.managementuser.domain.model.UserTypeEnum
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

class CreateEditUserViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var createUserUseCase: CreateUserUseCase

    @MockK
    private lateinit var editUserUseCase: EditUserUseCase

    @MockK
    private lateinit var getUserUseCase: GetUserUseCase

    @MockK
    private lateinit var getListPdmUseCase: GetListPdmUseCase

    @InjectMockKs
    private lateinit var createEditUserViewModel: CreateEditUserViewModel

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
    fun `GIVEN name changed WHEN updateUiName() is called THEN validate name was changed`() {
        // GIVEN
        val newName = "New Name"

        // WHEN
        createEditUserViewModel.updateUiName(newName)
        val currentUiName = createEditUserViewModel.uiState.value.collaborator.name

        // THEN
        Assertions.assertEquals(newName, currentUiName)
    }

    @Test
    fun `GIVEN email changed WHEN updateUiEmail() is called THEN validate email was changed`() {
        // GIVEN
        val newEmail = "New Email"

        // WHEN
        createEditUserViewModel.updateUiEmail(newEmail)
        val currentUiEmail = createEditUserViewModel.uiState.value.collaborator.email

        // THEN
        Assertions.assertEquals(newEmail, currentUiEmail)
    }

    @Test
    fun `GIVEN userType changed WHEN updateUiUserType() is called THEN validate userType was changed`() {
        // GIVEN
        val newUserType = UserTypeEnum.PDM.name

        // WHEN
        createEditUserViewModel.updateUiUserType(newUserType)
        val currentUiUserType = createEditUserViewModel.uiState.value.collaborator.type.name

        // THEN
        Assertions.assertEquals(newUserType, currentUiUserType)
    }

    @Test
    fun `GIVEN pdmEmail changed WHEN updateUiPdmEmail() is called THEN validate pdmEmail was changed`() {
        // GIVEN
        val newPdmEmail = "newemail@ciandt.com"

        // WHEN
        createEditUserViewModel.updateUiPdmEmail(newPdmEmail)
        val currentUiPdmEmail = createEditUserViewModel.uiState.value.collaborator.pdmEmail

        // THEN
        Assertions.assertEquals(newPdmEmail, currentUiPdmEmail)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN a user with edited information WHEN updateUiCurrentUser() is called THEN validate the resulting user with the modifications applied`() =
        runTest {
            // GIVEN
            val userId = 1
            val collaboratorUser = UserDataModel(
                id = userId,
                name = "New name",
                email = "New email",
                type = UserTypeEnum.PDM,
                pdmEmail = null
            )
            val newCurrentUser = CreateEditUserViewState(
                collaborator = collaboratorUser,
                errorMessage = "",
                isLoading = false,
                listPdm = emptyList(),
                pdm = null
            )

            coEvery { getUserUseCase.invoke(userId, listOf(collaboratorUser)) } returns collaboratorUser

            // WHEN
            createEditUserViewModel.updateUiCurrentUser(userId)
            advanceUntilIdle()

            val currentUiState = createEditUserViewModel.uiState.value

            // THEN
            Assertions.assertEquals(newCurrentUser, currentUiState)
        }

    @Test
    fun `GIVEN an error message WHEN updateUiErrorMessage() is called THEN validate errorMessage was changed`() {
        // GIVEN
        val errorMessage = "error message"

        // WHEN
        createEditUserViewModel.updateUiPdmEmail(errorMessage)
        val currentUiPdmEmail = createEditUserViewModel.uiState.value.collaborator.pdmEmail

        // THEN
        Assertions.assertEquals(errorMessage, currentUiPdmEmail)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN a user with new associated values WHEN createUser() is called THEN validate that the invoke() function was called`() =
        runTest {
            // GIVEN
            val currentUiState = createEditUserViewModel.uiState.value
            val finishedActivityCreateUser = {}

            coJustRun { createUserUseCase.invoke(
                collaborator = currentUiState.collaborator,
                pdm = currentUiState.pdm,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = any()
            )}

            // WHEN
            createEditUserViewModel.createUser(finishedActivityCreateUser = finishedActivityCreateUser)
            advanceUntilIdle()

            // THEN
            coVerify { createUserUseCase.invoke(
                collaborator = currentUiState.collaborator,
                pdm = currentUiState.pdm,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = any()
            )}
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN a user with modified values WHEN editUser() is called THEN validate the invoke() function was called`() =
        runTest {
            // GIVEN
            var currentUiState = createEditUserViewModel.uiState.value
            val finishedActivityCreateUser = {}
            val userId = 1
            val collaboratorUser = UserDataModel(
                id = userId,
                name = "New name",
                email = "New email",
                type = UserTypeEnum.PDM,
                pdmEmail = null
            )

            coEvery { getUserUseCase.invoke(userId, listOf(collaboratorUser)) } returns collaboratorUser
            coJustRun { editUserUseCase.invoke(
                collaborator = collaboratorUser,
                pdm = currentUiState.pdm,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = any()
            )}

            // WHEN
            createEditUserViewModel.updateUiCurrentUser(userId)
            createEditUserViewModel.editUser(finishedActivityCreateUser = finishedActivityCreateUser)
            advanceUntilIdle()

            currentUiState = createEditUserViewModel.uiState.value

            // THEN
            coVerify { editUserUseCase.invoke(
                collaborator = currentUiState.collaborator,
                pdm = currentUiState.pdm,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = any()
            )}
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN a list of pdm users WHEN getListPdm() is called THEN validate the return value of the invoke() function`() =
        runTest {
            // GIVEN
            val userFirst = UserDataModel(
                id = 1,
                name = "New name",
                email = "New email First",
                type = UserTypeEnum.PDM,
                pdmEmail = null
            )
            val userSecond = UserDataModel(
                id = 2,
                name = "New name",
                email = "New email Second",
                type = UserTypeEnum.PDM,
                pdmEmail = null
            )

            coEvery { getListPdmUseCase.invoke() } returns listOf(userFirst, userSecond)

            // WHEN
            createEditUserViewModel.getListPdm()
            advanceUntilIdle()

            val listPdm = createEditUserViewModel.uiState.value.listPdm

            // THEN
            Assertions.assertEquals(listOf(userFirst, userSecond), listPdm)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN an empty list of pdm users WHEN getListPdm() is called THEN validate the return value of the invoke() function is empty`() =
        runTest {
            // GIVEN
            coEvery { getListPdmUseCase.invoke() } returns emptyList<UserDataModel>()

            // WHEN
            createEditUserViewModel.getListPdm()
            advanceUntilIdle()

            val listPdm = createEditUserViewModel.uiState.value.listPdm

            // THEN
            Assertions.assertEquals(emptyList<UserDataModel>(), listPdm)
        }

    @Test
    fun `GIVEN mandatory fields is filled and user is a pdm WHEN isButtonEnable() is called THEN validate return true`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.PDM.name)

        // WHEN
        val result = createEditUserViewModel.isButtonEnable()

        // THEN
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `GIVEN mandatory fields is incomplete and user is admin WHEN isButtonEnable() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiName("")
        createEditUserViewModel.updateUiUserType(UserTypeEnum.ADMIN.name)

        // WHEN
        val result = createEditUserViewModel.isButtonEnable()

        // THEN
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `GIVEN mandatory fields is filled, user is Collaborator and pdmEmail is empty WHEN isButtonEnable() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.name)

        // WHEN
        val result = createEditUserViewModel.isButtonEnable()

        // THEN
        Assertions.assertEquals(false, result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN mandatory fields is filled, user is a collaborator and pdmEmail is filled WHEN isButtonEnable() is called THEN validate return true`() =
        runTest {
            // GIVEN
            val pdmUser = UserDataModel(
                id = 1,
                name = "New name",
                email = "pdmteste@ciandt.com",
                type = UserTypeEnum.PDM,
                pdmEmail = null
            )

            coEvery { getListPdmUseCase.invoke() } returns listOf(pdmUser)

            createEditUserViewModel.getListPdm()
            advanceUntilIdle()
            createEditUserViewModel.updateUiName("Lucas")
            createEditUserViewModel.updateUiEmail("lucas@ciandt.com")
            createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.name)
            createEditUserViewModel.updateUiPdmEmail("pdmteste@ciandt.com")

            // WHEN
            val result = createEditUserViewModel.isButtonEnable()

            // THEN
            Assertions.assertEquals(true, result)
        }

    @Test
    fun `GIVEN usertype is collaborator WHEN isCollaborator() is called THEN validate return true`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.name)

        // WHEN
        val result = createEditUserViewModel.isCollaborator()

        // THEN
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `GIVEN usertype isn't collaborator WHEN isCollaborator() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.PDM.name)

        // WHEN
        val result = createEditUserViewModel.isCollaborator()

        // THEN
        Assertions.assertEquals(false, result)
    }
}