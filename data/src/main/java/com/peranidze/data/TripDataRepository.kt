package com.peranidze.data

import com.peranidze.data.repository.TripRepository
import com.peranidze.data.source.trip.TripDataStoreFactory
import com.peranidze.data.trip.model.Trip
import io.reactivex.Completable
import io.reactivex.Single

class TripDataRepository(private val tripDataStoreFactory: TripDataStoreFactory) : TripRepository {

    override fun getTripsFor(userId: Long): Single<List<Trip>> {
        TODO("not implemented")
    }

    override fun updateTrip(trip: Trip): Completable {
        TODO("not implemented")
    }

    override fun deleteTrip(id: Long): Completable {
        TODO("not implemented")
    }

}