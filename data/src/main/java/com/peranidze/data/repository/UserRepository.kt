package com.peranidze.data.repository

import com.peranidze.data.user.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun logInUser(email: String, password: String): Single<User>

    fun signUpUser(email: String, password: String): Single<User>

    fun getUser(id: Long): Single<User>

    fun getUsers(): Single<List<User>>

    fun deleteUser(id: Long): Completable

    fun updateUser(user: User): Completable

}
