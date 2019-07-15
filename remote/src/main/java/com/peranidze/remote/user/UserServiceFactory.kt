package com.peranidze.remote.user

import com.peranidze.remote.HeaderInterceptor
import com.peranidze.remote.RetrofitFactory

object UserServiceFactory {

    fun makeUserServiceFactory(isDebug: Boolean, headerInterceptor: HeaderInterceptor): UserService =
        RetrofitFactory.makeRetrofit(isDebug, headerInterceptor).create(UserService::class.java)

}
