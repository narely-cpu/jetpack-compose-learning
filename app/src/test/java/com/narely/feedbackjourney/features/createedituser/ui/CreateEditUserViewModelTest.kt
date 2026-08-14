package com.narely.feedbackjourney.features.createedituser.ui

import androidx.compose.runtime.internal.composableLambda
import com.narely.feedbackjourney.features.createedituser.domain.CreateUserUseCase
import com.narely.feedbackjourney.features.createedituser.domain.EditUserUseCase
import com.narely.feedbackjourney.features.createedituser.domain.GetListPdmUseCase
import com.narely.feedbackjourney.features.createedituser.domain.GetUserUseCase
import com.narely.feedbackjourney.features.createedituser.domain.model.UserDataModel
import com.narely.feedbackjourney.features.createedituser.domain.model.UserTypeEnum
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
    fun `GIVEN any state changed WHEN updateUiState() is called THEN validate state change`() {
        // GIVEN
        val collaboratorUser = UserDataModel(
            id = 1,
            name = "savi",
            email = "savi@ciandt.com",
            type = UserTypeEnum.PDM,
            pdmEmail = null,
        )
        val newState = CreateEditUserViewState(
            collaborator = collaboratorUser,
            errorMessage = "",
            isLoading = false,
            listPdm = emptyList(),
            pdm = null
        )

        // WHEN
        createEditUserViewModel.updateUiState(newState)
        val currentUiState = createEditUserViewModel.uiState.value

        // THEN
        Assertions.assertEquals(newState, currentUiState)
    }

    @Test
    fun `GIVEN any information about the modified collaborator WHEN updateUiCollaborator() is called THEN validate information change`() {
        // GIVEN
        val collaboratorUser = UserDataModel(
            id = 2,
            name = "lucas",
            email = "lucas@ciandt.com",
            type = UserTypeEnum.COLLABORATOR,
            pdmEmail = "savi@ciandt.com",
        )

        // WHEN
        createEditUserViewModel.updateUiCollaborator(newCollaborator = collaboratorUser)
        val currentCollaborator = createEditUserViewModel.uiState.value.collaborator

        // THEN
        Assertions.assertEquals(collaboratorUser, currentCollaborator)
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

    @Test
    fun `GIVEN pdm changed WHEN updateUiPdm() is called THEN validate pdm was changed`() {
        // GIVEN
        val pdmUser = UserDataModel(
            id = 2,
            name = "lucas",
            email = "lucas@ciandt.com",
            type = UserTypeEnum.PDM,
            pdmEmail = null,
        )

        // WHEN
        createEditUserViewModel.updateUiPdm(pdmUser)
        val currentUiPdm = createEditUserViewModel.uiState.value.pdm

        // THEN
        Assertions.assertEquals(pdmUser, currentUiPdm)
    }

    @Test
    fun `GIVEN listPdm changed WHEN updateUiListPdm() is called THEN validate listPdm was changed`() {
        // GIVEN
        val pdmUser = UserDataModel(
            id = 1,
            name = "savi",
            email = "savi@ciandt.com",
            type = UserTypeEnum.PDM,
            pdmEmail = null,
        )
        val listPdm = listOf(pdmUser)

        // WHEN
        createEditUserViewModel.updateUiListPdm(listPdm)
        val currentUiListPdm = createEditUserViewModel.uiState.value.listPdm

        // THEN
        Assertions.assertEquals(currentUiListPdm, currentUiListPdm)
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

            coEvery { createEditUserViewModel.readUser(userId) } returns collaboratorUser

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

    @Test
    fun `GIVEN an existing user WHEN readUser() is called THEN validate return user `() =
        runTest {
            // GIVEN
            val userId = 1
            val userModel = UserDataModel(
                id = userId,
                name = "New name",
                email = "New email",
                type = UserTypeEnum.PDM,
                pdmEmail = null
            )

            coEvery { getUserUseCase.invoke(id = userId, listPdm = listOf(userModel))} returns userModel

            // WHEN
            val result = createEditUserViewModel.readUser(userId)

            // THEN
            Assertions.assertEquals(userModel, result)
        }

    @Test
    fun `GIVEN a user with an invalid id WHEN readUser() is called THEN validate return null`() =
        runTest {
            // GIVEN
            val incorrectId = 2
            val userModel = UserDataModel(
                id = 1,
                name = "New name",
                email = "New email",
                type = UserTypeEnum.PDM,
                pdmEmail = null
            )

            coEvery { getUserUseCase.invoke(incorrectId, listPdm = listOf(userModel)) } returns null

            // WHEN
            val result = createEditUserViewModel.readUser(incorrectId)

            // THEN
            Assertions.assertEquals(null, result)
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

            coEvery { createEditUserViewModel.readUser(userId) } returns collaboratorUser
            coJustRun { editUserUseCase.invoke(
                collaborator = currentUiState.collaborator,
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
    fun `GIVEN a non-existent user with modified values WHEN editUser() is called THEN validate the invoke() function was not called`() =
        runTest {
            // GIVEN
            var currentUiState = createEditUserViewModel.uiState.value
            val finishedActivityCreateUser = {}
            val userId = 1

            coEvery { createEditUserViewModel.readUser(userId) } returns null
            coJustRun { editUserUseCase.invoke(
                collaborator = currentUiState.collaborator,
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
            coVerify(exactly = 0) { editUserUseCase.invoke(
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
            val userFirst = UserDataModel(id = 1,
                name = "New name",
                email = "New email First",
                type = UserTypeEnum.PDM,
                pdmEmail = null
            )
            val userSecond = UserDataModel(id = 2,
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
    fun `GIVEN all mandatory fields are filled in WHEN areMandatoryFieldsFilled() is called THEN validate return true`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.name)

        // WHEN
        val result = createEditUserViewModel.areMandatoryFieldsFilled()

        // THEN
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `GIVEN some mandatory fields are filled in WHEN areMandatoryFieldsFilled() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiName("")

        // WHEN
        val result = createEditUserViewModel.areMandatoryFieldsFilled()

        // THEN
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `GIVEN a user who is a collaborator and an existing pdmEmail WHEN hasPdmAssigned() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.name)
        createEditUserViewModel.updateUiPdmEmail("emailpdm@ciandt.com")

        // WHEN
        val result = createEditUserViewModel.hasPdmAssigned()

        // THEN
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `GIVEN a user who is a collaborator and a non-existent pdm WHEN hasPdmAssigned() is called THEN validate return true`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.name)

        // WHEN
        val result = createEditUserViewModel.hasPdmAssigned()

        // THEN
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `GIVEN a user who is a pdm and pdm does not exist WHEN hasPdmAssigned() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.PDM.name)

        // WHEN
        val result = createEditUserViewModel.hasPdmAssigned()

        // THEN
        Assertions.assertEquals(false, result)
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

    @Test
    fun `GIVEN mandatory fields is filled, user is a collaborator and pdmEmail is filled WHEN isButtonEnable() is called THEN validate return true`() {
        // GIVEN
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