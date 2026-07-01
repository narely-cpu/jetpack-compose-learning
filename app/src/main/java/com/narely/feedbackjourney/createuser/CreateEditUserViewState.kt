package com.narely.feedbackjourney.createuser

data class CreateEditUserViewState(val name: String = "Name",
                                   val email: String = "Email",
                                   val password: String = "Password",
                                   val userType: String = "",
                                   val pdmId: String = "")
