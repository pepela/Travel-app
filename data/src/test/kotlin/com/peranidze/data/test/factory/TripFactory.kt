package com.peranidze.data.test.factory

import com.peranidze.data.test.factory.DataFactory.Factory.randomDate
import com.peranidze.data.test.factory.DataFactory.Factory.randomInt
import com.peranidze.data.test.factory.DataFactory.Factory.randomLong
import com.peranidze.data.test.factory.DataFactory.Factory.randomUuid
import com.peranidze.data.trip.model.Trip

class TripFactory {

    companion object Factory {

        fun makeTrip() = Trip(randomLong(), randomUuid(), randomDate(), randomDate(), randomUuid(), UserFactory.makeUser())

        fun makeTrips(count: Int = randomInt()) = List(count) { makeTrip() }
    }
}
