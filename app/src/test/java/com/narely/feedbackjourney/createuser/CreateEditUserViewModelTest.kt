package com.narely.feedbackjourney.createuser

import com.narely.feedbackjourney.core.model.UserDataModel
import com.narely.feedbackjourney.core.model.UserType
import com.narely.feedbackjourney.createuser.domain.CreateUserUseCase
import com.narely.feedbackjourney.createuser.domain.EditUserUseCase
import com.narely.feedbackjourney.createuser.domain.GetListPdmUseCase
import com.narely.feedbackjourney.createuser.domain.GetUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class CreateEditUserViewModelTest {

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

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN any state changed WHEN updateUiState() is called THEN validate state change`() {
        // GIVEN
        val newState = CreateEditUserViewState(
            id = "111232232",
            name = "New name",
            email = "New email",
            password = "New password",
            userType = UserType.PDM.userValue,
            pdmEmail = null)

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
    fun `GIVEN user exists WHEN readUser() is called THEN validate return user `() {
        // GIVEN
        val userModel = UserDataModel(id = "111232232",
            name = "New name",
            email = "New email",
            password = "New password",
            userType = UserType.PDM,
            pdmEmail = null)

        every { getUserUseCase.invoke(userModel.id)} returns userModel
        // WHEN
        val result = createEditUserViewModel.readUser("111232232")
        // THEN
        Assertions.assertEquals(userModel, result)
    }

    @Test
    fun `GIVEN user not exists WHEN readUser() is called THEN validate return null `() {
        // GIVEN
        every { getUserUseCase.invoke("1112322")} returns null

        // WHEN
        val result = createEditUserViewModel.readUser("1112322")

        // THEN
        Assertions.assertEquals(null, result)
    }

    @Test
    fun `GIVEN userId changed WHEN updateUiCurrentUser() is called THEN validate new userId`() {
        // GIVEN
        val userModel = UserDataModel(id = "111232232",
            name = "New name",
            email = "New email",
            password = "New password",
            userType = UserType.PDM,
            pdmEmail = null)
        val newCurrentUser = CreateEditUserViewState(
            id = userModel.id,
            name = userModel.name,
            email = userModel.email,
            password = userModel.password,
            userType = userModel.userType.userValue,
            pdmEmail = userModel.pdmEmail ?: "")
        every { getUserUseCase.invoke(userModel.id)} returns userModel

        // WHEN
        createEditUserViewModel.updateUiCurrentUser("111232232")
        val currentUiState = createEditUserViewModel.uiState.value

        // THEN
        Assertions.assertEquals(newCurrentUser, currentUiState)
    }
}