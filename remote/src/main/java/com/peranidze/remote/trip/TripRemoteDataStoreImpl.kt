package com.peranidze.remote.trip

import com.peranidze.data.source.trip.TripDataStore
import com.peranidze.data.trip.model.Trip
import com.peranidze.remote.trip.mapper.TripMapper
import com.peranidze.remote.trip.request.CreateTripRequestBody
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

class TripRemoteDataStoreImpl(
    private val tripService: TripService,
    private val tripMapper: TripMapper
) : TripDataStore {

    override fun createTrip(
        userId: Long,
        destination: String,
        startDate: Date,
        endDate: Date,
        comment: String?
    ): Flowable<Trip> =
        tripService.createTrip(CreateTripRequestBody(userId, destination, startDate, endDate, comment))
            .map {
                tripMapper.from(it)
            }
            .toFlowable()

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
