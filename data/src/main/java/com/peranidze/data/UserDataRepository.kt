package com.peranidze.data

import com.peranidze.data.repository.UserRepository
import com.peranidze.data.source.user.UserDataStoreFactory
import com.peranidze.data.user.model.User
import io.reactivex.Completable
import io.reactivex.Single

class UserDataRepository(private val userDataStoreFactory: UserDataStoreFactory) : UserRepository {

    override fun logInUser(email: String, password: String): Single<User> =
        userDataStoreFactory.getDataSource().logInUser(email, password)

    override fun signUpUser(email: String, password: String): Single<User> =
        userDataStoreFactory.getDataSource().signUpUser(email, password)

    override fun getUser(id: Long): Single<User> =
        userDataStoreFactory.getDataSource().getUser(id)

    override fun getUsers(): Single<List<User>> =
        userDataStoreFactory.getDataSource().getUsers()

    override fun deleteUser(id: Long): Completable {
        TODO("not implemented")
    }

    override fun updateUser(user: User): Completable {
        TODO("not implemented")
    }

}
