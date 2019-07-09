package com.peranidze.data.source.user

import com.peranidze.data.user.model.User
import io.reactivex.Completable
import io.reactivex.Flowable

interface UserDataStore {

    fun logInUser(email: String, password: String): Flowable<User>

    fun signUpUser(email: String, password: String): Flowable<User>

    fun getUser(id: Long): Flowable<User>

    fun getUsers(): Flowable<List<User>>

    fun deleteUser(id: Long): Completable

    fun updateUser(user: User): Flowable<User>

}
