package com.peranidze.travel.test.factory.trip

import com.peranidze.data.trip.model.Trip
import com.peranidze.travel.test.factory.DataFactory.Factory.randomDate
import com.peranidze.travel.test.factory.DataFactory.Factory.randomInt
import com.peranidze.travel.test.factory.DataFactory.Factory.randomLong
import com.peranidze.travel.test.factory.DataFactory.Factory.randomUuid
import com.peranidze.travel.test.factory.user.UserFactory

class TripFactory {

    companion object Factory {

        fun makeTrip() =
            Trip(randomLong(), randomUuid(), randomDate(), randomDate(), randomUuid(), UserFactory.makeUser())

        fun makeTrips(count: Int = randomInt()) = List(count) { makeTrip() }
    }
}
