package com.peranidze.travel.test.factory.trip

import com.peranidze.data.trip.model.Trip
import com.peranidze.travel.test.factory.DataFactory.Factory.randomDate
import com.peranidze.travel.test.factory.DataFactory.Factory.randomInt
import com.peranidze.travel.test.factory.DataFactory.Factory.randomLong
import com.peranidze.travel.test.factory.DataFactory.Factory.randomUuid

class TripFactory {

    companion object Factory {

        fun makeTrip() = Trip(randomLong(), randomUuid(), randomDate(), randomDate(), randomUuid())

        fun makeTrips(count: Int = randomInt()) = List(count) { makeTrip() }
    }
}