package com.peranidze.remote.trip

import com.peranidze.data.source.trip.TripDataStore
import com.peranidze.data.trip.model.Trip
import com.peranidze.remote.trip.mapper.TripMapper
import io.reactivex.Single

class TripRemoteDataStoreImpl(
    private val tripService: TripService,
    private val tripMapper: TripMapper
) : TripDataStore {

    override fun getTripsFor(userId: Long): Single<List<Trip>> =
        tripService.getTrips()
            .map {
                tripMapper.from(it)
            }

}
