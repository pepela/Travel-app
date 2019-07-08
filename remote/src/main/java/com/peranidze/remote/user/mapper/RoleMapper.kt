package com.peranidze.remote.user.mapper

import com.peranidze.data.user.model.Role
import com.peranidze.remote.EntityMapper
import com.peranidze.remote.user.model.RoleModel

open class RoleMapper : EntityMapper<RoleModel, Role> {

    override fun from(model: RoleModel): Role =
        when (model) {
            RoleModel.REGULAR -> Role.REGULAR
            RoleModel.MANAGER -> Role.MANAGER
            RoleModel.ADMIN -> Role.ADMIN
        }

    override fun toModel(entity: Role): RoleModel =
        when (entity) {
            Role.REGULAR -> RoleModel.REGULAR
            Role.MANAGER -> RoleModel.MANAGER
            Role.ADMIN -> RoleModel.ADMIN
        }
}
