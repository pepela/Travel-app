package com.peranidze.remote.user.mock

import com.peranidze.remote.user.UserService
import com.peranidze.remote.user.model.RoleModel
import com.peranidze.remote.user.model.UserModel
import com.peranidze.remote.user.request.LogInRequestBody
import com.peranidze.remote.user.request.SignUpRequestBody
import io.reactivex.Completable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class UserServiceMockImpl : UserService {

    val users = listOf(
        UserModel(1L, "admin@test.ge", RoleModel.ADMIN, "ASDADS"),
        UserModel(2L, "manager@test.ge", RoleModel.MANAGER, "Asdasdasd"),
        UserModel(3L, "user2@test.ge", RoleModel.REGULAR, "12w121wsadws"),
        UserModel(4L, "user3@test.ge", RoleModel.REGULAR, "12w121wsadws"),
        UserModel(5L, "user4@test.ge", RoleModel.REGULAR, "12w121wsadws"),
        UserModel(6L, "user5@test.ge", RoleModel.REGULAR, "12w121wsadws")
    )

    override fun logIn(logInRequestBody: LogInRequestBody): Single<UserModel> =
        Single.timer(3, TimeUnit.SECONDS)
            .flatMap {
                Single.just(
                    if (logInRequestBody.login.toLowerCase().contains("admin")) users[0]
                    else users[2]
                )
            }

    override fun signUp(signUpRequestBody: SignUpRequestBody): Single<UserModel> =
        Single.timer(3, TimeUnit.SECONDS).flatMap { Single.just(users.first()) }

    override fun getUser(id: Long): Single<UserModel> =
        Single.just(users.first { it.id == id })

    override fun getUsers(): Single<List<UserModel>> =
        Single.timer(2, TimeUnit.SECONDS).flatMap { Single.just(users) }

    override fun updateUser(id: Long, userModel: UserModel): Single<UserModel> =
        Single.timer(2, TimeUnit.SECONDS).flatMap { Single.just(userModel) }

    override fun deleteUser(id: Long): Completable =
        Completable.complete()

}
