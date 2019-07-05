package com.peranidze.remote.user

import com.peranidze.remote.RetrofitFactory

object UserServiceFactory {

    fun makeUserServiceFactory(isDebug: Boolean): UserService =
        RetrofitFactory.makeRetrofit(isDebug).create(UserService::class.java)
    
}
