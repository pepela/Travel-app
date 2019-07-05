package com.peranidze.data.source.trip

import com.peranidze.data.trip.model.Trip
import io.reactivex.Single

interface TripDataStore {

    fun getTripsFor(userId: Long): Single<List<Trip>>

}
