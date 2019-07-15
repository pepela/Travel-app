package com.peranidze.data.source.user

import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import io.reactivex.Completable
import io.reactivex.Flowable

interface UserDataStore {

    fun logInUser(login: String, password: String): Flowable<User>

    fun register(login: String, email: String, password: String): Flowable<User>

    fun getUser(login: String): Flowable<User>

    fun getUsers(): Flowable<List<User>>

    fun deleteUser(login: String): Completable

    fun updateUser(user:User): Flowable<User>

    fun createUser(login: String, email: String, roles: List<Role>): Flowable<User>

}
