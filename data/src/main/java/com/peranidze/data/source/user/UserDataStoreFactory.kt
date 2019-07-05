package com.peranidze.data.source.user

open class UserDataStoreFactory(private val remote: UserDataStore) {

    open fun getDataSource(): UserDataStore = remote

}
