package com.peranidze.remote.user.model

data class UserModel(
    val id: Long,
    val login: String,
    val email: String,
    val authorities: List<RoleModel>?,
    val jwt: String?
)
