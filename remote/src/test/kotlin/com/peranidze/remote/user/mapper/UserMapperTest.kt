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
        const val USER_ID = 1L
        const val USER_EMAIL = "mock@mockito.com"
        const val TOKEN = "mock123token"
        val USER_MODEL_ROLE = RoleModel.REGULAR
        val USER_ROLE = Role.REGULAR
    }

    @Before
    fun setup() {
        whenever(roleMapper.from(RoleModel.REGULAR)).thenReturn(Role.REGULAR)
        whenever(roleMapper.from(RoleModel.ADMIN)).thenReturn(Role.ADMIN)
        whenever(roleMapper.from(RoleModel.MANAGER)).thenReturn(Role.MANAGER)
    }

    @Test
    fun `maps model to entity`() {
        val userModel = UserModel(USER_ID, USER_EMAIL, USER_MODEL_ROLE, TOKEN)
        val userShouldBe = User(USER_ID, USER_EMAIL, USER_ROLE, TOKEN)
        val mappedUser = userMapper.from(userModel)

        assertEquals(userShouldBe, mappedUser)
    }
}
