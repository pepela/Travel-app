package com.peranidze.cache

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

open class PreferenceHelper(context: Context) {

    companion object {
        private const val PREFERENCE_NAME = "com.peranidze.travel"

        private const val PREFERENCE_USER_LOGGED_IN = "user_logged_in"
    }


    private val prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE)
    }

    var isUserLoggedIn: Boolean
        get() = prefs.getBoolean(PREFERENCE_USER_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(PREFERENCE_USER_LOGGED_IN, value).apply()

}
