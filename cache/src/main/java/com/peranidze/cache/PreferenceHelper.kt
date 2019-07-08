package com.peranidze.cache

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.peranidze.data.user.model.Role

open class PreferenceHelper(context: Context) {

    companion object {
        private const val PREFERENCE_NAME = "com.peranidze.travel"

        private const val PREFERENCE_USER_LOGGED_IN = "user_logged_in"
        private const val PREFERENCE_USER_ROLE = "user_role"
    }


    private val prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE)
    }

    var isUserLoggedIn: Boolean
        get() = prefs.getBoolean(PREFERENCE_USER_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(PREFERENCE_USER_LOGGED_IN, value).apply()

    var userRole: Role
        get() = Role.toRoleEnum(prefs.getString(PREFERENCE_USER_ROLE, null))
        set(value) = prefs.edit().putString(PREFERENCE_USER_ROLE, value.name).apply()

    fun clear(){
        prefs.edit().clear().apply()
    }
}
