package com.peranidze.remote.trip

import com.peranidze.data.source.trip.TripDataStore
import com.peranidze.data.trip.model.Trip
import com.peranidze.remote.trip.mapper.TripMapper
import io.reactivex.Completable
import io.reactivex.Flowable

class TripRemoteDataStoreImpl(
    private val tripService: TripService,
    private val tripMapper: TripMapper
) : TripDataStore {

    override fun getTrip(id: Long): Flowable<Trip> =
        tripService.getTrip(id)
            .map {
                tripMapper.from(it)
            }
            .toFlowable()

    override fun getTripsFor(userId: Long): Flowable<List<Trip>> =
        tripService.getTrips()
            .map {
                tripMapper.from(it)
            }
            .toFlowable()

    override fun updateTrip(trip: Trip): Flowable<Trip> =
        tripService.updateTrip(trip.id, tripMapper.toModel(trip))
            .map {
                tripMapper.from(it)
            }
            .toFlowable()

    override fun deleteTrip(id: Long): Completable =
        tripService.deleteTrip(id)
}
