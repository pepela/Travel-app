package com.peranidze.travel.user

import com.peranidze.data.user.model.User

sealed class UpdateUserState(val data: User? = null, val errorMessage: String? = null) {

    data class Success(private val user: User? = null) : UpdateUserState(user)

    data class Error(private val message: String?) : UpdateUserState(errorMessage = message)

    object Loading : UpdateUserState()
}
