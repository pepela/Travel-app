package com.peranidze.remote.trip

import com.peranidze.remote.RetrofitFactory

object TripServiceFactory {

    fun makeTripService(isDebug: Boolean): TripService =
        RetrofitFactory.makeRetrofit(isDebug).create(TripService::class.java)

}
