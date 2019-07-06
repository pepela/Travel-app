package com.peranidze.remote.trip.mock

import com.peranidze.remote.trip.TripService
import com.peranidze.remote.trip.model.TripModel
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

class TripServiceMockImpl : TripService {

    override fun getTrip(): Single<TripModel> = Single.just(TripModel(1, "Future", Date(), Date()))

    override fun getTrips(): Single<List<TripModel>> =
        Single.timer(5, TimeUnit.SECONDS)
            .flatMap {
                Single.just(
                    listOf(
                        TripModel(1L, "destination1", Date(), Date()),
                        TripModel(2L, "destination2", Date(), Date()),
                        TripModel(3L, "destination3", Date(), Date()),
                        TripModel(4L, "destination4", Date(), Date()),
                        TripModel(5L, "destination5", Date(), Date())
                    )
                )
            }
}