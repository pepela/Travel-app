package com.peranidze.remote.user.mapper

import com.peranidze.data.user.model.Role
import com.peranidze.remote.user.model.RoleModel
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class RoleMapperTest {

    private val roleMapper = RoleMapper()

    @Test
    fun `maps admin role model to admin entity`() {
        val roleModel = RoleModel.ADMIN
        val role = roleMapper.from(roleModel)
        assertEquals(role, Role.ADMIN)
    }

    @Test
    fun `maps manager role model to manager entity`() {
        val roleModel = RoleModel.MANAGER
        val role = roleMapper.from(roleModel)
        assertEquals(role, Role.MANAGER)
    }

    @Test
    fun `maps regular role model to regular entity`() {
        val roleModel = RoleModel.REGULAR
        val role = roleMapper.from(roleModel)
        assertEquals(role, Role.REGULAR)
    }
}
