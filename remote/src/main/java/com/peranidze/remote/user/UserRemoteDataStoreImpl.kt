package com.peranidze.remote.user

import com.peranidze.data.source.user.UserDataStore
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import com.peranidze.remote.user.mapper.RoleMapper
import com.peranidze.remote.user.mapper.UserMapper
import com.peranidze.remote.user.request.CreateUserRequestBody
import com.peranidze.remote.user.request.LogInRequestBody
import com.peranidze.remote.user.request.SignUpRequestBody
import com.peranidze.remote.user.request.UpdateUserRequestBody
import io.reactivex.Completable
import io.reactivex.Flowable

class UserRemoteDataStoreImpl(
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val roleMapper: RoleMapper
) :
    UserDataStore {

    override fun logInUser(login: String, password: String): Flowable<User> =
        userService.logIn(LogInRequestBody(login, password))
            .map {
                userMapper.from(it)
            }
            .toFlowable()

    override fun register(login: String, email: String, password: String): Flowable<User> =
        userService.register(SignUpRequestBody(login, email, password))
            .map {
                userMapper.from(it)
            }
            .toFlowable()

    override fun getUser(login: String): Flowable<User> =
        userService.getUser(login)
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

    override fun deleteUser(login: String): Completable =
        userService.deleteUser(login)

    override fun updateUser(user: User): Flowable<User> =
        with(userMapper.toModel(user)) {
            userService.updateUser(UpdateUserRequestBody(id, login, email, authorities!!))
                .map {
                    userMapper.from(it)
                }
                .toFlowable()
        }

    override fun createUser(login: String, email: String, roles: List<Role>): Flowable<User> =
        userService.createUser(CreateUserRequestBody(login, email, roleMapper.toModels(roles)))
            .map {
                userMapper.from(it)
            }
            .toFlowable()
}
