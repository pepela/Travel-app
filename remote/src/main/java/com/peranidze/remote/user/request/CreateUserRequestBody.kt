package com.peranidze.remote.user.request

import com.peranidze.remote.user.model.RoleModel

data class CreateUserRequestBody(
    val login: String,
    val email: String,
    val authorities: List<RoleModel>,
    val activated: Boolean = true
)
