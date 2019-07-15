package com.peranidze.travel.test.factory.user

import com.peranidze.data.user.model.Role
import kotlin.random.Random

class RoleFactory {

    companion object Factory {
        fun makeRole(): Role = Role.REGULAR

        fun makeRoles(): List<Role> = listOf(makeRole())
    }
}
