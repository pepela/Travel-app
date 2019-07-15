package com.peranidze.remote.user.mapper

import com.peranidze.data.user.model.User
import com.peranidze.remote.EntityMapper
import com.peranidze.remote.user.model.UserModel

open class UserMapper(private val roleMapper: RoleMapper) : EntityMapper<UserModel, User> {

    override fun from(model: UserModel): User =
        with(model) {
            User(id, login, email, if (authorities != null) roleMapper.from(authorities) else emptyList(), jwt)
        }

    override fun toModel(entity: User): UserModel =
        with(entity) {
            UserModel(id, login, email, roleMapper.toModels(roles), token)
        }
}

