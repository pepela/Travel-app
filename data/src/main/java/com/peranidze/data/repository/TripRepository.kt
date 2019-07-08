package com.peranidze.data.repository

import com.peranidze.data.trip.model.Trip
import io.reactivex.Completable
import io.reactivex.Single

interface TripRepository {

    fun getTrip(id: Long): Single<Trip>

    fun getTripsFor(userId: Long): Single<List<Trip>>

    fun updateTrip(trip: Trip): Single<Trip>

    fun deleteTrip(id: Long): Completable

}
