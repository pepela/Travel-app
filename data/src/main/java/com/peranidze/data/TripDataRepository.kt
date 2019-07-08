package com.peranidze.data

import com.peranidze.data.repository.TripRepository
import com.peranidze.data.source.trip.TripDataStoreFactory
import com.peranidze.data.trip.model.Trip
import io.reactivex.Completable
import io.reactivex.Single

class TripDataRepository(private val tripDataStoreFactory: TripDataStoreFactory) : TripRepository {

    override fun getTrip(id: Long): Single<Trip> =
        tripDataStoreFactory.getDataSource().getTrip(id)

    override fun getTripsFor(userId: Long): Single<List<Trip>> =
        tripDataStoreFactory.getDataSource().getTripsFor(userId)

    override fun updateTrip(trip: Trip): Single<Trip> =
        tripDataStoreFactory.getDataSource().updateTrip(trip)

    override fun deleteTrip(id: Long): Completable =
        tripDataStoreFactory.getDataSource().deleteTrip(id)
}
