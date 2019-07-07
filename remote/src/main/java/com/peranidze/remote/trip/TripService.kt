package com.peranidze.remote.trip

import com.peranidze.remote.trip.model.TripModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TripService {

    @GET("")
    fun getTrip(@Query("id") id: Long): Single<TripModel>

    @GET("")
    fun getTrips(): Single<List<TripModel>>

}
