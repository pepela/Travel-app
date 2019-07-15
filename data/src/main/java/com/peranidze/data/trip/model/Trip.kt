package com.peranidze.data.trip.model

import com.peranidze.data.user.model.User
import java.util.*

data class Trip(
    val id: Long,
    val destination: String,
    val startDate: Date,
    val endDate: Date,
    val comment: String? = null,
    val user: User
)

