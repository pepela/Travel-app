package com.peranidze.remote.user

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.remote.user.mapper.RoleMapper
import com.peranidze.remote.user.mapper.UserMapper
import com.peranidze.test.user.UserModelFactory
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserRemoteDataStoreImplTest {

    companion object {
        private const val USER_ID = 1L
        private const val EMAIL = "mock@mock.mock"
        private const val PASSWORD = "mockerino"

        private val USER_MODEL = UserModelFactory.makeUserModel()
        private val USERS = UserModelFactory.makeUserModels()
    }

    private val userService = mock<UserService>()
    private val roleMapper = RoleMapper()
    private val userMapper = UserMapper(roleMapper)
    private val tripRemoteDataStoreImpl = UserRemoteDataStoreImpl(userService, userMapper)

    @Before
    fun setup() {
        whenever(userService.getUsers()).thenReturn(Single.just(USERS))
        whenever(userService.logIn(any())).thenReturn(Single.just(USER_MODEL))
        whenever(userService.signUp(any())).thenReturn(Single.just(USER_MODEL))
        whenever(userService.getUser(any())).thenReturn(Single.just(USER_MODEL))
        whenever(userService.deleteUser(any())).thenReturn(Completable.complete())
        whenever(userService.updateUser(any(), any())).thenReturn(Single.just(USER_MODEL))
    }

    @Test
    fun `logInUser returns user`() {
        val testSubscriber = tripRemoteDataStoreImpl.logInUser(EMAIL, PASSWORD).test()
        testSubscriber.assertValue(userMapper.from(USER_MODEL))
    }

    @Test
    fun `signUpUser returns user`() {
        val testSubscriber = tripRemoteDataStoreImpl.signUpUser(EMAIL, PASSWORD).test()
        testSubscriber.assertValue(userMapper.from(USER_MODEL))
    }

    @Test
    fun `getUser returns user`() {
        val testSubscriber = tripRemoteDataStoreImpl.getUser(USER_ID).test()
        testSubscriber.assertValue(userMapper.from(USER_MODEL))
    }

    @Test
    fun `getUsers returns users`() {
        val testSubscriber = tripRemoteDataStoreImpl.getUsers().test()
        testSubscriber.assertValue(userMapper.from(USERS))
    }


    @Test
    fun `updateUser returns user`() {
        val testSubscriber = tripRemoteDataStoreImpl.updateUser(userMapper.from(USER_MODEL)).test()
        testSubscriber.assertValue(userMapper.from(USER_MODEL))
    }

    @Test
    fun `deleteUser completes`() {
        val testSubscriber = tripRemoteDataStoreImpl.deleteUser(USER_ID).test()
        testSubscriber.assertComplete()
    }

}
