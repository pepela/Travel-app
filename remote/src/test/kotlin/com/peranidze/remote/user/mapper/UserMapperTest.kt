package com.peranidze.remote.user.mapper

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import com.peranidze.remote.user.model.RoleModel
import com.peranidze.remote.user.model.UserModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class UserMapperTest {

    private val roleMapper = mock<RoleMapper>()
    private val userMapper = UserMapper(roleMapper)

    companion object {
        private const val USER_ID = 1L
        private const val USER_LOGIN = "username"
        private const val USER_EMAIL = "mock@mockito.com"
        private const val TOKEN = "mock123token"
        private val USER_MODEL_ROLES = listOf(RoleModel.ROLE_USER, RoleModel.ROLE_MANAGER)
        private val USER_ROLES = listOf(Role.REGULAR, Role.MANAGER)
    }

    @Before
    fun setup() {
        whenever(roleMapper.from(RoleModel.ROLE_USER)).thenReturn(Role.REGULAR)
        whenever(roleMapper.from(RoleModel.ROLE_ADMIN)).thenReturn(Role.ADMIN)
        whenever(roleMapper.from(RoleModel.ROLE_MANAGER)).thenReturn(Role.MANAGER)
        whenever(roleMapper.from(USER_MODEL_ROLES)).thenReturn(USER_ROLES)

        whenever(roleMapper.toModel(Role.REGULAR)).thenReturn(RoleModel.ROLE_USER)
        whenever(roleMapper.toModel(Role.ADMIN)).thenReturn(RoleModel.ROLE_ADMIN)
        whenever(roleMapper.toModel(Role.MANAGER)).thenReturn(RoleModel.ROLE_MANAGER)
        whenever(roleMapper.toModels(USER_ROLES)).thenReturn(USER_MODEL_ROLES)
    }

    @Test
    fun `maps model to entity`() {
        val userModel = UserModel(USER_ID, USER_LOGIN, USER_EMAIL, USER_MODEL_ROLES, TOKEN)
        val userShouldBe = User(USER_ID, USER_LOGIN, USER_EMAIL, USER_ROLES, TOKEN)
        val mappedUser = userMapper.from(userModel)

        assertEquals(userShouldBe, mappedUser)
    }

    @Test
    fun `maps entity to model`() {
        val user = User(USER_ID, USER_LOGIN, USER_EMAIL, USER_ROLES, TOKEN)
        val userShouldBe = UserModel(USER_ID, USER_LOGIN, USER_EMAIL, USER_MODEL_ROLES, TOKEN)
        val mappedUser = userMapper.toModel(user)

        assertEquals(userShouldBe, mappedUser)
    }
}
