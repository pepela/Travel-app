package com.peranidze.travel.user

import com.peranidze.data.user.model.User

sealed class DeleteUserState(val data: User? = null, val errorMessage: String? = null) {

    data class Success(private val user: User? = null) : DeleteUserState(user)

    data class Error(private val message: String?) : DeleteUserState(errorMessage = message)

    object Loading : DeleteUserState()
}
