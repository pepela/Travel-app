package com.peranidze.travel.test.factory.user

import com.peranidze.data.user.model.Role

class RoleFactory {

    companion object Factory {
        fun makeRole() = Role.MANAGER
    }
}
