package com.peranidze.remote.trip.request

import com.peranidze.remote.user.model.UserModel
import java.util.*

data class CreateTripRequestBody(
    val destination: String,
    val startDate: Date,
    val endDate: Date,
    val comment: String?,
    val user: UserModel?
)
