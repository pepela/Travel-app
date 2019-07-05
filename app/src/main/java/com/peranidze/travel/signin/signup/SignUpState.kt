package com.peranidze.travel.signin.signup

import com.peranidze.data.user.model.User

sealed class SignUpState(var data: User? = null, var errorMessage: String? = null) {

    data class Success(private val trips: User) : SignUpState(trips)

    data class Error(private val message: String?) : SignUpState(errorMessage = message)

    object Loading : SignUpState()

}
