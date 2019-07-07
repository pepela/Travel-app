package com.peranidze.remote.trip.mock

import com.peranidze.remote.trip.TripService
import com.peranidze.remote.trip.model.TripModel
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

class TripServiceMockImpl : TripService {

    override fun getTrip(): Single<TripModel> = Single.just(TripModel(1, "Future", Date(), Date()))

    override fun getTrips(): Single<List<TripModel>> =
        Single.timer(2, TimeUnit.SECONDS)
            .flatMap {
                Single.just(
                    listOf(
                        TripModel(1L, "london", Date(1542490367181), Date(1582490367181)),
                        TripModel(2L, "paris", Date(1562490367181), Date(1562490367181)),
                        TripModel(3L, "goa", Date(1562490367181), Date(1563490367181)),
                        TripModel(4L, "khazbegi", Date(1562220367181), Date(1564490367181)),
                        TripModel(5L, "mars", Date(1562490367181), Date(1565490367181)),
                        TripModel(6L, "future", Date(1561490364181), Date(1566490367181)),
                        TripModel(7L, "destination5", Date(1562490363181), Date(1567490367181)),
                        TripModel(8L, "destination5", Date(1612490362181), Date(1668490367181)),
                        TripModel(9L, "destination5", Date(1522490367181), Date(1562490367181)),
                        TripModel(10L, "destination5", Date(1562190361181), Date(1562490367181)),
                        TripModel(11L, "destination5", Date(1502450365181), Date(1512490367181)),
                        TripModel(12L, "destination5", Date(1562290367181), Date(1562490367181)),
                        TripModel(13L, "destination5", Date(1462190364181), Date(1512490367181))
                    )
                )
            }
}
