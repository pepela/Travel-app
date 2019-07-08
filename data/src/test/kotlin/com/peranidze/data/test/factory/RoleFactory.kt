package com.peranidze.data.test.factory

import com.peranidze.data.user.model.Role

class RoleFactory {

    companion object Factory {
        fun makeRole() = Role.REGULAR
    }
}