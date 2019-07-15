package com.peranidze.remote.trip

import com.peranidze.remote.HeaderInterceptor
import com.peranidze.remote.RetrofitFactory

object TripServiceFactory {

    fun makeTripService(isDebug: Boolean, headerInterceptor: HeaderInterceptor): TripService =
        RetrofitFactory.makeRetrofit(isDebug, headerInterceptor).create(TripService::class.java)

}
