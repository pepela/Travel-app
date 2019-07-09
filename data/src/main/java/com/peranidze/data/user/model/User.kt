package com.peranidze.data.user.model

data class User(val id: Long, val email: String, val role: Role, val token: String, val password: String? = null)
