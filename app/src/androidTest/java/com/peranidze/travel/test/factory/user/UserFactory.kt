package com.peranidze.travel.test.factory.user

import com.peranidze.data.user.model.User
import com.peranidze.travel.test.factory.DataFactory.Factory.randomInt
import com.peranidze.travel.test.factory.DataFactory.Factory.randomLong
import com.peranidze.travel.test.factory.DataFactory.Factory.randomUuid
import com.peranidze.travel.test.factory.user.RoleFactory.Factory.makeRole

class UserFactory {

    companion object Factory {

        fun makeUser() = User(randomLong(), randomUuid(), randomUuid(), listOf(makeRole()), randomUuid())

        fun makeUsers(count: Int = randomInt()): List<User> = List(count) { makeUser() }
    }
}
