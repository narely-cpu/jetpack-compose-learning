package com.narely.feedbackjourney.createuser

data class CreateEditUserViewState(val id: Int? = null,
                                   val name: String = "Name",
                                   val email: String = "Email",
                                   val password: String = "Password",
                                   val userType: String = "",
                                   val pdmEmail: String? = null,
                                   val isLoading: Boolean = false,
                                   val listPdm: List<String> = emptyList(),
)
