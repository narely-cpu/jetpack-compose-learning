package com.narely.feedbackjourney.createuser

enum class UserType(val userType: String) {
    ADMIN("Admin"),
    COLLABORATOR("Collaborator"),
    PDM("PDM")
}
data class UserData(val id: String,
                    val name: String,
                    val email: String,
                    val password: String,
//                    val userType: UserType,
                    val pdmId: String?)