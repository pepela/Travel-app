package com.peranidze.remote.user.model

data class UserModel(val id: Long, val email: String, val role: RoleModel, val token: String)
