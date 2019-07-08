package com.peranidze.remote.user.mapper

import com.peranidze.data.user.model.User
import com.peranidze.remote.EntityMapper
import com.peranidze.remote.user.model.UserModel

open class UserMapper(private val roleMapper: RoleMapper) : EntityMapper<UserModel, User> {

    override fun from(model: UserModel): User =
        with(model) {
            User(id, email, roleMapper.from(role), token)
        }

    override fun toModel(entity: User): UserModel =
        with(entity) {
            UserModel(id, email, roleMapper.toModel(role), token)
        }
}

