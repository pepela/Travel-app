package com.peranidze.test.user

import com.peranidze.remote.user.model.UserModel
import com.peranidze.test.DataFactory.Factory.randomInt
import com.peranidze.test.DataFactory.Factory.randomLong
import com.peranidze.test.DataFactory.Factory.randomUuid
import com.peranidze.test.user.RoleModelFactory.Factory.makeRoleModel

class UserModelFactory {

    companion object Factory {

        fun makeUserModel() = UserModel(randomLong(), randomUuid(), makeRoleModel())

        fun makeUserModels(count: Int = randomInt()): List<UserModel> = List(count) { makeUserModel() }
    }
}