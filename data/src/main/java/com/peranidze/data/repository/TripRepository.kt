package com.peranidze.data.repository

import com.peranidze.data.trip.model.Trip
import com.peranidze.data.user.model.User
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

interface TripRepository {

    fun createTrip(destination: String, startDate: Date, endDate: Date, comment: String?, user: User?): Flowable<Trip>

    fun getTrip(id: Long): Flowable<Trip>

    fun getTripsFor(userId: Long): Flowable<List<Trip>>

    fun updateTrip(trip: Trip): Flowable<Trip>

    fun deleteTrip(id: Long): Completable

}
