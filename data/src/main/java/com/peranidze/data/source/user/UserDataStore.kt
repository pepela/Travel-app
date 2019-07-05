package com.peranidze.data.source.user

import com.peranidze.data.user.model.User
import io.reactivex.Single

interface UserDataStore {

    fun logInUser(email: String, password: String): Single<User>

    fun signUpUser(email: String, password: String): Single<User>

    fun getUsers(): Single<List<User>>

}
