package com.peranidze.test.trip

import com.peranidze.remote.trip.model.TripModel
import com.peranidze.test.DataFactory.Factory.randomDate
import com.peranidze.test.DataFactory.Factory.randomInt
import com.peranidze.test.DataFactory.Factory.randomLong
import com.peranidze.test.DataFactory.Factory.randomUuid

class TripModelFactory {

    companion object Factory {

        fun makeTripModel() = TripModel(randomLong(), randomUuid(), randomDate(), randomDate(), randomUuid())

        fun makeTripModels(count: Int = randomInt()) = List(count) { makeTripModel() }
    }
}
