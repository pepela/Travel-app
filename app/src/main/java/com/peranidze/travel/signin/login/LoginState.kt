package com.peranidze.travel.signin.login

import com.peranidze.data.user.model.User

sealed class LoginState(var data: User? = null, var errorMessage: String? = null) {

    data class Success(private val trips: User) : LoginState(trips)

    data class Error(private val message: String?) : LoginState(errorMessage = message)

    object Loading : LoginState()

}
