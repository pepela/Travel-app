package com.peranidze.remote.user

import com.peranidze.data.source.user.UserDataStore
import com.peranidze.data.user.model.User
import com.peranidze.remote.user.mapper.UserMapper
import com.peranidze.remote.user.request.LogInRequestBody
import com.peranidze.remote.user.request.SignUpRequestBody
import io.reactivex.Completable
import io.reactivex.Flowable

class UserRemoteDataStoreImpl(
    private val userService: UserService,
    private val userMapper: UserMapper
) : UserDataStore {

    override fun logInUser(email: String, password: String): Flowable<User> =
        userService.logIn(LogInRequestBody(email, password))
            .map {
                userMapper.from(it)
            }
            .toFlowable()

    override fun signUpUser(email: String, password: String): Flowable<User> =
        userService.signUp(SignUpRequestBody(email, password))
            .map {
                userMapper.from(it)
            }
            .toFlowable()

    override fun getUser(id: Long): Flowable<User> =
        userService.getUser(id)
            .map {
                userMapper.from(it)
            }
            .toFlowable()

    override fun getUsers(): Flowable<List<User>> =
        userService.getUsers()
            .map {
                userMapper.from(it)
            }
            .toFlowable()

    override fun deleteUser(id: Long): Completable =
        userService.deleteUser(id)


    override fun updateUser(user: User): Flowable<User> =
        userService.updateUser(user.id, userMapper.toModel(user))
            .map {
                userMapper.from(it)
            }
            .toFlowable()
}
