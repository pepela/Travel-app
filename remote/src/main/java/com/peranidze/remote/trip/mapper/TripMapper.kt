package com.peranidze.remote.trip.mapper

import com.peranidze.data.trip.model.Trip
import com.peranidze.remote.EntityMapper
import com.peranidze.remote.trip.model.TripModel
import com.peranidze.remote.user.mapper.UserMapper

open class TripMapper(private val userMapper: UserMapper) : EntityMapper<TripModel, Trip> {

    override fun from(model: TripModel): Trip =
        with(model) {
            Trip(id, destination, startDate, endDate, comment, userMapper.from(user))
        }

    override fun toModel(entity: Trip): TripModel =
        with(entity) {
            TripModel(id, destination, startDate, endDate, comment, userMapper.toModel(user))
        }
}
