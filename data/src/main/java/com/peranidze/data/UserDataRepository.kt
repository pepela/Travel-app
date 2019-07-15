package com.peranidze.data

import com.peranidze.data.repository.UserRepository
import com.peranidze.data.source.user.UserDataStoreFactory
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import io.reactivex.Completable
import io.reactivex.Flowable

class UserDataRepository(private val userDataStoreFactory: UserDataStoreFactory) : UserRepository {

    override fun logInUser(login: String, password: String): Flowable<User> =
        userDataStoreFactory.getDataSource().logInUser(login, password)

    override fun register(login: String, email: String, password: String): Flowable<User> =
        userDataStoreFactory.getDataSource().register(login, email, password)

    override fun getUser(login: String): Flowable<User> =
        userDataStoreFactory.getDataSource().getUser(login)

    override fun getUsers(): Flowable<List<User>> =
        userDataStoreFactory.getDataSource().getUsers()

    override fun deleteUser(login: String): Completable =
        userDataStoreFactory.getDataSource().deleteUser(login)

    override fun updateUser(user: User): Flowable<User> =
        userDataStoreFactory.getDataSource().updateUser(user)

    override fun createUser(login: String, email: String, roles: List<Role>): Flowable<User> =
        userDataStoreFactory.getDataSource().createUser(login, email, roles)

}
