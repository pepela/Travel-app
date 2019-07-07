package com.peranidze.remote.user

import com.peranidze.data.source.user.UserDataStore
import com.peranidze.data.user.model.User
import com.peranidze.remote.user.mapper.UserMapper
import com.peranidze.remote.user.request.LogInRequestBody
import com.peranidze.remote.user.request.SignUpRequestBody
import io.reactivex.Completable
import io.reactivex.Single

class UserRemoteDataStoreImpl(
    private val userService: UserService,
    private val userMapper: UserMapper
) : UserDataStore {

    override fun logInUser(email: String, password: String): Single<User> =
        userService.logIn(LogInRequestBody(email, password))
            .map {
                userMapper.from(it)
            }

    override fun signUpUser(email: String, password: String): Single<User> =
        userService.signUp(SignUpRequestBody(email, password))
            .map {
                userMapper.from(it)
            }

    override fun getUser(id: Long): Single<User> =
        userService.getUser(id)
            .map {
                userMapper.from(it)
            }

    override fun getUsers(): Single<List<User>> =
        userService.getUsers()
            .map {
                userMapper.from(it)
            }

    override fun deleteUser(id: Long): Completable {
        TODO("not implemented")
    }

    override fun updateUser(user: User): Completable {
        TODO("not implemented")
    }
}
