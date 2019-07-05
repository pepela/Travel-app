package com.peranidze.travel.extensions

import android.util.Patterns

fun String.isEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()
