package com.peranidze.travel.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toDateString(): String = SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault()).format(this)

