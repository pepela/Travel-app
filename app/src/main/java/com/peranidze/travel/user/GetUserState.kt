package com.peranidze.travel.user

import com.peranidze.data.user.model.User

sealed class GetUserState(val data: User? = null, val errorMessage: String? = null) {

    data class Success(private val user: User? = null) : GetUserState(user)

    data class Error(private val message: String?) : GetUserState(errorMessage = message)

    object Loading : GetUserState()
}
