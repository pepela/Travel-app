package com.peranidze.data

import com.peranidze.data.repository.UserRepository
import com.peranidze.data.source.user.UserDataStoreFactory
import com.peranidze.data.user.model.User
import io.reactivex.Completable
import io.reactivex.Flowable

class UserDataRepository(private val userDataStoreFactory: UserDataStoreFactory) : UserRepository {

    override fun logInUser(email: String, password: String): Flowable<User> =
        userDataStoreFactory.getDataSource().logInUser(email, password)

    override fun signUpUser(email: String, password: String): Flowable<User> =
        userDataStoreFactory.getDataSource().signUpUser(email, password)

    override fun getUser(id: Long): Flowable<User> =
        userDataStoreFactory.getDataSource().getUser(id)

    override fun getUsers(): Flowable<List<User>> =
        userDataStoreFactory.getDataSource().getUsers()

    override fun deleteUser(id: Long): Completable =
        userDataStoreFactory.getDataSource().deleteUser(id)

    override fun updateUser(user: User): Flowable<User> =
        userDataStoreFactory.getDataSource().updateUser(user)

}
