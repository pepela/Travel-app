package com.peranidze.remote.trip.mapper

import com.peranidze.data.trip.model.Trip
import com.peranidze.remote.trip.model.TripModel
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class TripMapperTest {

    private val tripMapper = TripMapper()

    companion object {
        const val TRIP_ID = 1L
        const val TRIP_DESTINATION = "mock destination"
        val TRIP_START_DATE = Date()
        val TRIP_END_DATE = Date()
        const val TRIP_COMMENT = "mock comment"
    }

    @Test
    fun `maps model to entity`() {
        val tripModel = TripModel(TRIP_ID, TRIP_DESTINATION, TRIP_START_DATE, TRIP_END_DATE, TRIP_COMMENT)
        val tripShouldBe = Trip(TRIP_ID, TRIP_DESTINATION, TRIP_START_DATE, TRIP_END_DATE, TRIP_COMMENT)
        val mappedTrip = tripMapper.from(tripModel)

        assertEquals(tripShouldBe, mappedTrip)
    }

    @Test
    fun `maps entity to model`() {
        val trip = Trip(TRIP_ID, TRIP_DESTINATION, TRIP_START_DATE, TRIP_END_DATE, TRIP_COMMENT)
        val tripShouldBe = TripModel(TRIP_ID, TRIP_DESTINATION, TRIP_START_DATE, TRIP_END_DATE, TRIP_COMMENT)
        val mappedTrip = tripMapper.toModel(trip)

        assertEquals(tripShouldBe, mappedTrip)
    }
}
