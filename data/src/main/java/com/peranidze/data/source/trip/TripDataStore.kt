package com.peranidze.data.source.trip

import com.peranidze.data.trip.model.Trip
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

interface TripDataStore {

    fun createTrip(userId: Long, destination: String, startDate: Date, endDate: Date, comment: String?): Flowable<Trip>

    fun getTrip(id: Long): Flowable<Trip>

    fun getTripsFor(userId: Long): Flowable<List<Trip>>

    fun updateTrip(trip: Trip): Flowable<Trip>

    fun deleteTrip(id: Long): Completable
}
