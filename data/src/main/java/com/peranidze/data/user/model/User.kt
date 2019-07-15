package com.peranidze.data.user.model

data class User(
    val id: Long,
    val login: String,
    val email: String,
    val roles: List<Role>,
    val token: String?
)
