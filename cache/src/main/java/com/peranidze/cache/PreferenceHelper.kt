package com.peranidze.cache

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.peranidze.data.user.model.Role

open class PreferenceHelper(context: Context) {

    companion object {
        private const val PREFERENCE_NAME = "com.peranidze.travel"

        private const val PREFERENCE_USER_LOGGED_IN = "user_logged_in"
        private const val PREFERENCE_IS_ADMIN = "is_admin"
        private const val PREFERENCE_IS_MANAGER = "is_manager"
        private const val PREFERENCE_USER_ID = "preference_user_id"
        private const val PREFERENCE_USER_LOGIN = "preference_user_login"
        private const val PREFERENCE_TOKEN = "preference_user_token"
    }


    private val prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE)
    }

    var isUserLoggedIn: Boolean
        get() = prefs.getBoolean(PREFERENCE_USER_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(PREFERENCE_USER_LOGGED_IN, value).apply()

    var isAdmin:Boolean
        get() = prefs.getBoolean(PREFERENCE_IS_ADMIN, false)
        set(value) = prefs.edit().putBoolean(PREFERENCE_IS_ADMIN, value).apply()

    var isManager:Boolean
        get() = prefs.getBoolean(PREFERENCE_IS_MANAGER, false)
        set(value) = prefs.edit().putBoolean(PREFERENCE_IS_MANAGER, value).apply()

    var userId: Long
        get() = prefs.getLong(PREFERENCE_USER_ID, -1)
        set(value) = prefs.edit().putLong(PREFERENCE_USER_ID, value).apply()

    var login: String?
        get() = prefs.getString(PREFERENCE_USER_LOGIN, null)
        set(value) = prefs.edit().putString(PREFERENCE_USER_LOGIN, value).apply()

    var token: String?
        get() = prefs.getString(PREFERENCE_TOKEN, null)
        set(value) = prefs.edit().putString(PREFERENCE_TOKEN, value).apply()

    fun clear() {
        prefs.edit().clear().apply()
    }
}
