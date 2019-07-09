package com.peranidze.data.repository

import com.peranidze.data.trip.model.Trip
import io.reactivex.Completable
import io.reactivex.Flowable

interface TripRepository {

    fun getTrip(id: Long): Flowable<Trip>

    fun getTripsFor(userId: Long): Flowable<List<Trip>>

    fun updateTrip(trip: Trip): Flowable<Trip>

    fun deleteTrip(id: Long): Completable

}
