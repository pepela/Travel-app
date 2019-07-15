package com.peranidze.remote.trip.model

import com.peranidze.remote.user.model.UserModel
import java.util.*

data class TripModel(
    val id: Long,
    val destination: String,
    val startDate: Date,
    val endDate: Date,
    val comment: String? = null,
    val user: UserModel
)

