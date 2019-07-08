package com.peranidze.remote.trip

import com.peranidze.data.source.trip.TripDataStore
import com.peranidze.data.trip.model.Trip
import com.peranidze.remote.trip.mapper.TripMapper
import io.reactivex.Completable
import io.reactivex.Single

class TripRemoteDataStoreImpl(
    private val tripService: TripService,
    private val tripMapper: TripMapper
) : TripDataStore {

    override fun getTrip(id: Long): Single<Trip> =
        tripService.getTrip(id)
            .map {
                tripMapper.from(it)
            }

    override fun getTripsFor(userId: Long): Single<List<Trip>> =
        tripService.getTrips()
            .map {
                tripMapper.from(it)
            }

    override fun updateTrip(trip: Trip): Single<Trip> =
        tripService.updateTrip(trip.id, tripMapper.toModel(trip))
            .map {
                tripMapper.from(it)
            }

    override fun deleteTrip(id: Long): Completable =
        tripService.deleteTrip(id)
}
