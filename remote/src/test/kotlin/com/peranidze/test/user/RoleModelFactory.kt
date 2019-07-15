package com.peranidze.test.user

import com.peranidze.remote.user.model.RoleModel

class RoleModelFactory {

    companion object Factory {
        fun makeRoleModel() = RoleModel.ROLE_USER
    }
}
