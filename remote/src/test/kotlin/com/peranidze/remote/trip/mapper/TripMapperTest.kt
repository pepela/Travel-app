package com.peranidze.remote.trip.mapper

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.data.trip.model.Trip
import com.peranidze.data.user.model.User
import com.peranidze.remote.trip.model.TripModel
import com.peranidze.remote.user.mapper.UserMapper
import com.peranidze.remote.user.model.UserModel
import com.peranidze.test.DataFactory.Factory.randomLong
import com.peranidze.test.DataFactory.Factory.randomUuid
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class TripMapperTest {

    private val userMapper = mock<UserMapper>()
    private val tripMapper = TripMapper(userMapper)

    companion object {
        private const val TRIP_ID = 1L
        private const val TRIP_DESTINATION = "mock destination"
        private const val TRIP_COMMENT = "mock comment"
        private val TRIP_START_DATE = Date()
        private val TRIP_END_DATE = Date()
        private val USER = User(randomLong(), randomUuid(), randomUuid(), emptyList(), randomUuid())
        private val USER_MODEL = UserModel(randomLong(), randomUuid(), randomUuid(), emptyList(), randomUuid())
    }

    @Before
    fun setup() {
        whenever(userMapper.from(USER_MODEL)).thenReturn(USER)
        whenever(userMapper.toModel(USER)).thenReturn(USER_MODEL)
    }

    @Test
    fun `maps model to entity`() {
        val tripModel = TripModel(TRIP_ID, TRIP_DESTINATION, TRIP_START_DATE, TRIP_END_DATE, TRIP_COMMENT, USER_MODEL)
        val tripShouldBe = Trip(TRIP_ID, TRIP_DESTINATION, TRIP_START_DATE, TRIP_END_DATE, TRIP_COMMENT, USER)
        val mappedTrip = tripMapper.from(tripModel)

        assertEquals(tripShouldBe, mappedTrip)
    }

    @Test
    fun `maps entity to model`() {
        val trip = Trip(TRIP_ID, TRIP_DESTINATION, TRIP_START_DATE, TRIP_END_DATE, TRIP_COMMENT, USER)
        val tripShouldBe =
            TripModel(TRIP_ID, TRIP_DESTINATION, TRIP_START_DATE, TRIP_END_DATE, TRIP_COMMENT, USER_MODEL)
        val mappedTrip = tripMapper.toModel(trip)

        assertEquals(tripShouldBe, mappedTrip)
    }
}
