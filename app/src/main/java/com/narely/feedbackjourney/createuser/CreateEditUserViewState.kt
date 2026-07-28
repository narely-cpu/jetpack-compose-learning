package com.narely.feedbackjourney.createuser

data class CreateEditUserViewState(var id: Int? = null,
                                   val name: String = "Name",
                                   val email: String = "Email",
                                   val password: String = "Password",
                                   val userType: String = "",
                                   val pdmEmail: String? = null)
