package com.narely.feedbackjourney.core.singleton

object AuthSingleton {
    private var idToken: String? = null

    fun setToken(token: String) {
        idToken = token
    }

    fun getToken(): String? {
        return idToken
    }
}