package com.peranidze.remote.trip.mock

import com.peranidze.remote.trip.TripService
import com.peranidze.remote.trip.model.TripModel
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

class TripServiceMockImpl : TripService {

    val trips = listOf(
        TripModel(1L, "london", Date(1562789972304), Date(1562799979999), "comment is here"),
        TripModel(2L, "paris", Date(1562490367181), Date(1562490367181), "comment is here"),
        TripModel(3L, "goa", Date(1562490367181), Date(1563490367181), "comment is here"),
        TripModel(4L, "khazbegi", Date(1592220367181), Date(1664490367181)),
        TripModel(5L, "mars", Date(1562490367181), Date(1565490367181), "comment is here"),
        TripModel(6L, "future", Date(1561490364181), Date(1566490367181), "comment is here"),
        TripModel(7L, "destination5", Date(1562490363181), Date(1567490367181)),
        TripModel(8L, "destination5", Date(1612490362181), Date(1668490367181), "comment is here"),
        TripModel(9L, "destination5", Date(1522490367181), Date(1562490367181), "comment is here"),
        TripModel(10L, "destination5", Date(1562190361181), Date(1562490367181)),
        TripModel(11L, "destination5", Date(1502450365181), Date(1512490367181), "comment is here"),
        TripModel(12L, "destination5", Date(1572290367181), Date(1582490367181), "comment is here"),
        TripModel(13L, "destination5", Date(1462190364181), Date(1512490367181), "no comment")
    )

    override fun getTrip(id: Long): Single<TripModel> =
        Single.timer(2, TimeUnit.SECONDS).flatMap { Single.just(trips.first { it.id == id }) }

    override fun getTrips(): Single<List<TripModel>> =
        Single.timer(2, TimeUnit.SECONDS).flatMap { Single.just(trips) }

    override fun updateTrip(id: Long, tripModel: TripModel): Single<TripModel> =
        Single.timer(1, TimeUnit.SECONDS).flatMap { Single.just(tripModel) }

    override fun deleteTrip(id: Long): Completable = Completable.complete()
}
