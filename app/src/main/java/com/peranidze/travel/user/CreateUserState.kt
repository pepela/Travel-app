package com.peranidze.travel.user

import com.peranidze.data.user.model.User

sealed class CreateUserState(val data: User? = null, val errorMessage: String? = null) {

    data class Success(private val user: User? = null) : CreateUserState(user)

    data class Error(private val message: String?) : CreateUserState(errorMessage = message)

    object Loading : CreateUserState()
}
