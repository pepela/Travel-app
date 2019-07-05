package com.peranidze.remote.trip

import com.peranidze.remote.trip.model.TripModel
import io.reactivex.Single
import retrofit2.http.GET

interface TripService {

    @GET("")
    fun getTrip(): Single<TripModel>

    @GET("")
    fun getTrips(): Single<List<TripModel>>

}
