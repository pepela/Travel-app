package com.peranidze.travel.trips

import com.peranidze.data.trip.model.Trip

sealed class TripsState(var data: List<Trip>? = null, var errorMessage: String? = null) {

    data class Success(private val trips: List<Trip>) : TripsState(trips)

    data class Error(private val message: String?) : TripsState(errorMessage = message)

    object Loading : TripsState()

}
