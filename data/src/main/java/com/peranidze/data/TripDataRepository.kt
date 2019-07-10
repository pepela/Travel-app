package com.peranidze.data

import com.peranidze.data.repository.TripRepository
import com.peranidze.data.source.trip.TripDataStoreFactory
import com.peranidze.data.trip.model.Trip
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

class TripDataRepository(private val tripDataStoreFactory: TripDataStoreFactory) : TripRepository {

    override fun createTrip(
        userId: Long,
        destination: String,
        startDate: Date,
        endDate: Date,
        comment: String?
    ): Flowable<Trip> =
        tripDataStoreFactory.getDataSource().createTrip(userId, destination, startDate, endDate, comment)

    override fun getTrip(id: Long): Flowable<Trip> =
        tripDataStoreFactory.getDataSource().getTrip(id)

    override fun getTripsFor(userId: Long): Flowable<List<Trip>> =
        tripDataStoreFactory.getDataSource().getTripsFor(userId)

    override fun updateTrip(trip: Trip): Flowable<Trip> =
        tripDataStoreFactory.getDataSource().updateTrip(trip)

    override fun deleteTrip(id: Long): Completable =
        tripDataStoreFactory.getDataSource().deleteTrip(id)
}
