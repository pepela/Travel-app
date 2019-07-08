package com.peranidze.data.test.factory

import com.peranidze.data.test.factory.DataFactory.Factory.randomInt
import com.peranidze.data.test.factory.DataFactory.Factory.randomLong
import com.peranidze.data.test.factory.DataFactory.Factory.randomUuid
import com.peranidze.data.test.factory.RoleFactory.Factory.makeRole
import com.peranidze.data.user.model.User

class UserFactory {

    companion object Factory {

        fun makeUser() = User(randomLong(), randomUuid(), makeRole(), randomUuid())

        fun makeUsers(count: Int = randomInt()): List<User> = List(count) { makeUser() }
    }
}