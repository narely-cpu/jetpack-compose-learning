package com.narely.feedbackjourney.createedituser

import com.narely.feedbackjourney.createedituser.domain.model.UserDataModel
import com.narely.feedbackjourney.createedituser.ui.UserTypeEnum
import com.narely.feedbackjourney.createedituser.domain.CreateUserUseCase
import com.narely.feedbackjourney.createedituser.domain.EditUserUseCase
import com.narely.feedbackjourney.createedituser.domain.GetListPdmUseCase
import com.narely.feedbackjourney.createedituser.domain.GetUserUseCase
import com.narely.feedbackjourney.createedituser.ui.CreateEditUserViewModel
import com.narely.feedbackjourney.createedituser.ui.CreateEditUserViewState
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
        val newState = CreateEditUserViewState(
            id = 1,
            name = "New name",
            email = "New email",
            password = "New password",
            userType = UserTypeEnum.PDM.userValue,
            pdmEmail = null
        )

        // WHEN
        createEditUserViewModel.updateUiState(newState)
        val currentUiState = createEditUserViewModel.uiState.value

        // THEN
        Assertions.assertEquals(newState, currentUiState)
    }

    @Test
    fun `GIVEN name changed WHEN updateUiName() is called THEN validate name was changed`() {
        // GIVEN
        val newName = "New Name"

        // WHEN
        createEditUserViewModel.updateUiName(newName)
        val currentUiState = createEditUserViewModel.uiState.value

        // THEN
        Assertions.assertEquals(newName, currentUiState.name)
    }

    @Test
    fun `GIVEN email changed WHEN updateUiEmail() is called THEN validate email was changed`() {
        // GIVEN
        val newEmail = "New Email"

        // WHEN
        createEditUserViewModel.updateUiEmail(newEmail)
        val currentUiState = createEditUserViewModel.uiState.value

        // THEN
        Assertions.assertEquals(newEmail, currentUiState.email)
    }

    @Test
    fun `GIVEN password changed WHEN updateUiPassword() is called THEN validate password was changed`() {
        // GIVEN
        val newPassword = "New Email"

        // WHEN
        createEditUserViewModel.updateUiPassword(newPassword)
        val currentUiState = createEditUserViewModel.uiState.value

        // THEN
        Assertions.assertEquals(newPassword, currentUiState.password)
    }

    @Test
    fun `GIVEN userType changed WHEN updateUiUserType() is called THEN validate userType was changed`() {
        // GIVEN
        val newUserType = "PDM"

        // WHEN
        createEditUserViewModel.updateUiUserType(newUserType)
        val currentUiState = createEditUserViewModel.uiState.value

        // THEN
        Assertions.assertEquals(newUserType, currentUiState.userType)
    }

    @Test
    fun `GIVEN pdmEmail changed WHEN updateUiPdmEmail() is called THEN validate pdmEmail was changed`() {
        // GIVEN
        val newPdmEmail = "newemail@ciandt.com"

        // WHEN
        createEditUserViewModel.updateUiPdmEmail(newPdmEmail)
        val currentUiState = createEditUserViewModel.uiState.value

        // THEN
        Assertions.assertEquals(newPdmEmail, currentUiState.pdmEmail)
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
                password = "New password",
                type = UserTypeEnum.PDM.userValue,
                pdmEmail = null)

            coEvery { getUserUseCase.invoke(userModel.id)} returns userModel

            // WHEN
            val result = createEditUserViewModel.readUser(userId)

            // THEN
            Assertions.assertEquals(userModel, result)
        }

    @Test
    fun `GIVEN a user with an invalid id WHEN readUser() is called THEN validate return null`() =
        runTest {
            // GIVEN
            val incorrectId = 1

            coEvery { getUserUseCase.invoke(incorrectId) } returns null

            // WHEN
            val result = createEditUserViewModel.readUser(incorrectId)

            // THEN
            Assertions.assertEquals(null, result)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN a user with edited information WHEN updateUiCurrentUser() is called THEN validate the resulting user with the modifications applied`() =
        runTest {
            // GIVEN
            val userId = 1
            val userModel = UserDataModel(
                id = userId,
                name = "New name",
                email = "New email",
                password = "New password",
                type = UserTypeEnum.PDM.userValue,
                pdmEmail = null
            )
            val newCurrentUser = CreateEditUserViewState(
                id = userModel.id,
                name = userModel.name,
                email = userModel.email,
                password = userModel.password,
                userType = userModel.type,
                pdmEmail = userModel.pdmEmail
            )

            coEvery { createEditUserViewModel.readUser(userId) } returns userModel

            // WHEN
            createEditUserViewModel.updateUiCurrentUser(userId)
            advanceUntilIdle()

            val currentUiState = createEditUserViewModel.uiState.value

            // THEN
            Assertions.assertEquals(newCurrentUser, currentUiState)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN a user with new associated values WHEN createUser() is called THEN validate that the invoke() function was called`() =
        runTest {
            // GIVEN
            val currentUiState = createEditUserViewModel.uiState.value
            val finishedActivityCreateUser = {}

            coJustRun { createUserUseCase.invoke(
                name = currentUiState.name,
                email = currentUiState.email,
                userType = currentUiState.userType,
                pdmEmail = currentUiState.pdmEmail,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = any()
                )
            }

            // WHEN
            createEditUserViewModel.createUser(finishedActivityCreateUser = finishedActivityCreateUser)
            advanceUntilIdle()

            // THEN
            coVerify { createUserUseCase.invoke(
                name = currentUiState.name,
                email = currentUiState.email,
                userType = currentUiState.userType,
                pdmEmail = currentUiState.pdmEmail,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = any()
                )
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN a user with modified values WHEN editUser() is called THEN validate the invoke() function was called`() =
        runTest {
            // GIVEN
            var currentUiState = createEditUserViewModel.uiState.value
            val finishedActivityCreateUser = {}
            val userId = 1
            val userModel = UserDataModel(
                id = userId,
                name = "Name",
                email = "Email",
                password = "Password",
                type = "",
                pdmEmail = null
            )

            coEvery { createEditUserViewModel.readUser(userId) } returns userModel
            coJustRun { editUserUseCase.invoke(
                id = userId,
                name = currentUiState.name,
                email = currentUiState.email,
                userType = currentUiState.userType,
                pdmEmail = currentUiState.pdmEmail,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = any()
            ) }

            // WHEN
            createEditUserViewModel.updateUiCurrentUser(userId)
            createEditUserViewModel.editUser(finishedActivityCreateUser = finishedActivityCreateUser)
            advanceUntilIdle()

            currentUiState = createEditUserViewModel.uiState.value

            // THEN
            coVerify { editUserUseCase.invoke(
                id = userId,
                name = currentUiState.name,
                email = currentUiState.email,
                userType = currentUiState.userType,
                pdmEmail = currentUiState.pdmEmail,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = any()
                )
            }
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
                id = userId,
                name = currentUiState.name,
                email = currentUiState.email,
                userType = currentUiState.userType,
                pdmEmail = currentUiState.pdmEmail,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = any()
                )
            }

            // WHEN
            createEditUserViewModel.updateUiCurrentUser(userId)
            createEditUserViewModel.editUser(finishedActivityCreateUser = finishedActivityCreateUser)
            advanceUntilIdle()

            currentUiState = createEditUserViewModel.uiState.value

            // THEN
            coVerify(exactly = 0) { editUserUseCase.invoke(
                id = userId,
                name = currentUiState.name,
                email = currentUiState.email,
                userType = currentUiState.userType,
                pdmEmail = currentUiState.pdmEmail,
                finishedActivityCreateUser = finishedActivityCreateUser,
                errorMessage = any()
                )
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN a list of pdm users WHEN getListPdm() is called THEN validate the return value of the invoke() function`() =
        runTest {
            // GIVEN
            val userFirst = UserDataModel(id = 1,
                name = "New name",
                email = "New email First",
                password = "New password",
                type = UserTypeEnum.PDM.userValue,
                pdmEmail = null
            )
            val userSecond = UserDataModel(id = 2,
                name = "New name",
                email = "New email Second",
                password = "New password",
                type = UserTypeEnum.PDM.userValue,
                pdmEmail = null
            )

            coEvery { getListPdmUseCase.invoke() } returns listOf(userFirst.email, userSecond.email)

            // WHEN
            createEditUserViewModel.getListPdm()
            advanceUntilIdle()

            val listPdm = createEditUserViewModel.uiState.value.listPdm

            // THEN
            Assertions.assertEquals(listOf(userFirst.email, userSecond.email), listPdm)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN an empty list of pdm users WHEN getListPdm() is called THEN validate the return value of the invoke() function is empty`() =
        runTest {
            // GIVEN
            coEvery { getListPdmUseCase.invoke() } returns listOf()

            // WHEN
            createEditUserViewModel.getListPdm()
            advanceUntilIdle()

            val listPdm = createEditUserViewModel.uiState.value.listPdm

            // THEN
            Assertions.assertEquals(listOf<String>(), listPdm)
        }

    @Test
    fun `GIVEN all mandatory fields are filled in WHEN areMandatoryFieldsFilled() is called THEN validate return true`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.userValue)

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
    fun `GIVEN a user who is a collaborator and an existing pdmEmail WHEN needPDMAssignedOrIsEmptyPdmEmailField() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.userValue)
        createEditUserViewModel.updateUiPdmEmail("emailpdm@ciandt.com")

        // WHEN
        val result = createEditUserViewModel.needPDMAssignedOrIsEmptyPdmEmailField()

        // THEN
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `GIVEN a user who is a collaborator and a non-existent pdm WHEN needPDMAssignedOrIsEmptyPdmEmailField() is called THEN validate return true`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.userValue)

        // WHEN
        val result = createEditUserViewModel.needPDMAssignedOrIsEmptyPdmEmailField()

        // THEN
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `GIVEN a user who is a pdm and pdm does not exist WHEN needPDMAssignedOrIsEmptyPdmEmailField() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.PDM.userValue)

        // WHEN
        val result = createEditUserViewModel.needPDMAssignedOrIsEmptyPdmEmailField()

        // THEN
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `GIVEN mandatory fields is filled and user is a pdm WHEN isButtonEnable() is called THEN validate return true`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.PDM.userValue)

        // WHEN
        val result = createEditUserViewModel.isButtonEnable()

        // THEN
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `GIVEN mandatory fields is incomplete and user is admin WHEN isButtonEnable() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiName("")
        createEditUserViewModel.updateUiUserType(UserTypeEnum.ADMIN.userValue)

        // WHEN
        val result = createEditUserViewModel.isButtonEnable()

        // THEN
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `GIVEN mandatory fields is filled, user is Collaborator and pdmEmail is empty WHEN isButtonEnable() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.userValue)

        // WHEN
        val result = createEditUserViewModel.isButtonEnable()

        // THEN
        Assertions.assertEquals(false, result)
    }

    @Test
    fun `GIVEN mandatory fields is filled, user is a collaborator and pdmEmail is filled WHEN isButtonEnable() is called THEN validate return true`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.userValue)
        createEditUserViewModel.updateUiPdmEmail("pdmteste@ciandt.com")

        // WHEN
        val result = createEditUserViewModel.isButtonEnable()

        // THEN
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `GIVEN usertype is collaborator WHEN isCollaborator() is called THEN validate return true`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.COLLABORATOR.userValue)

        // WHEN
        val result = createEditUserViewModel.isCollaborator()

        // THEN
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `GIVEN usertype isn't collaborator WHEN isCollaborator() is called THEN validate return false`() {
        // GIVEN
        createEditUserViewModel.updateUiUserType(UserTypeEnum.PDM.userValue)

        // WHEN
        val result = createEditUserViewModel.isCollaborator()

        // THEN
        Assertions.assertEquals(false, result)
    }
}