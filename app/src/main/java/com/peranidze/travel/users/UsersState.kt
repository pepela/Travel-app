package com.peranidze.travel.users

import com.peranidze.data.user.model.User

sealed class UsersState(val data: List<User>? = null, val errorMessage: String? = null) {

    data class Success(private val user: List<User>) : UsersState(user)

    data class Error(private val message: String?) : UsersState(errorMessage = message)

    object Loading : UsersState()
}
