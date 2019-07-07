package com.peranidze.travel.user

import com.peranidze.data.user.model.User

sealed class UserState(val data: User? = null, val errorMessage: String? = null) {

    data class Success(private val user: User) : UserState(user)

    data class Error(private val message: String?) : UserState(errorMessage = message)

    object Loading : UserState()
}
