package com.peranidze.remote.trip.request

import java.util.*

data class CreateTripRequestBody(
    val userId: Long,
    val destination: String,
    val startDate: Date,
    val endDate: Date,
    val comment: String?
)
