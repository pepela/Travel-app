package com.peranidze.remote.user.mapper

import com.peranidze.data.user.model.Role
import com.peranidze.remote.EntityMapper
import com.peranidze.remote.user.model.RoleModel

open class RoleMapper : EntityMapper<RoleModel, Role> {

    override fun from(model: RoleModel): Role =
        when (model) {
            RoleModel.ROLE_USER -> Role.REGULAR
            RoleModel.ROLE_MANAGER -> Role.MANAGER
            RoleModel.ROLE_ADMIN -> Role.ADMIN
        }

    override fun toModel(entity: Role): RoleModel =
        when (entity) {
            Role.REGULAR -> RoleModel.ROLE_USER
            Role.MANAGER -> RoleModel.ROLE_MANAGER
            Role.ADMIN -> RoleModel.ROLE_ADMIN
        }
}
