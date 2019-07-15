package com.peranidze.remote.trip

import com.peranidze.data.source.trip.TripDataStore
import com.peranidze.data.trip.model.Trip
import com.peranidze.data.user.model.User
import com.peranidze.remote.extensions.withErrorHandling
import com.peranidze.remote.trip.mapper.TripMapper
import com.peranidze.remote.trip.request.CreateTripRequestBody
import com.peranidze.remote.user.mapper.UserMapper
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

class TripRemoteDataStoreImpl(
    private val tripService: TripService,
    private val tripMapper: TripMapper,
    private val userMapper: UserMapper
) : TripDataStore {

    override fun createTrip(destination: String, startDate: Date, endDate: Date, comment: String?, user: User?)
            : Flowable<Trip> =
        tripService.createTrip(
            CreateTripRequestBody(
                destination,
                startDate,
                endDate,
                comment,
                if (user != null) userMapper.toModel(user) else null
            )
        )
            .withErrorHandling()
            .map {
                tripMapper.from(it)
            }
            .toFlowable()

    override fun getTrip(id: Long): Flowable<Trip> =
        tripService.getTrip(id)
            .map {
                tripMapper.from(it)
            }
            .withErrorHandling()
            .toFlowable()

    override fun getTripsFor(userId: Long): Flowable<List<Trip>> =
        tripService.getTrips()
            .map {
                tripMapper.from(it)
            }
            .withErrorHandling()
            .toFlowable()

    override fun updateTrip(trip: Trip): Flowable<Trip> =
        tripService.updateTrip(tripMapper.toModel(trip))
            .map {
                tripMapper.from(it)
            }
            .withErrorHandling()
            .toFlowable()

    override fun deleteTrip(id: Long): Completable =
        tripService.deleteTrip(id).withErrorHandling()
}
