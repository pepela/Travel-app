package com.peranidze.remote.trip.mapper

import com.peranidze.data.trip.model.Trip
import com.peranidze.remote.EntityMapper
import com.peranidze.remote.trip.model.TripModel

open class TripMapper : EntityMapper<TripModel, Trip> {

    override fun from(model: TripModel): Trip =
        with(model) {
            Trip(id, destination, startDate, endDate, comment)
        }

}
