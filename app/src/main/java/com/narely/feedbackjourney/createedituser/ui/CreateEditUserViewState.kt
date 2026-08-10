package com.narely.feedbackjourney.createedituser.ui

data class CreateEditUserViewState(
    val id: Int? = null,
    val name: String = "Name",
    val email: String = "Email",
    val userType: String = "",
    val pdmEmail: String? = null,
    val isLoading: Boolean = false,
    val listPdm: List<String>? = emptyList(),
    val errorMessage: String? = "",
)
