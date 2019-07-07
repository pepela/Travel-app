package com.peranidze.travel.trip

import com.peranidze.data.trip.model.Trip
import com.peranidze.data.user.model.User

sealed class TripAndUsersState(var data: Pair<Trip, List<User>>? = null, var errorMessage: String? = null) {

    data class Success(private val tripAndUsers: Pair<Trip, List<User>>) : TripAndUsersState(tripAndUsers)

    data class Error(private val message: String?) : TripAndUsersState(errorMessage = message)

    object Loading : TripAndUsersState()

}
