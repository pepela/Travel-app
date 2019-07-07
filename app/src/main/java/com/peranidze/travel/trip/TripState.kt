package com.peranidze.travel.trip

import com.peranidze.data.trip.model.Trip

sealed class TripState(var data: Trip? = null, var errorMessage: String? = null) {

    data class Success(private val trip: Trip) : TripState(trip)

    data class Error(private val message: String?) : TripState(errorMessage = message)

    object Loading : TripState()

}
