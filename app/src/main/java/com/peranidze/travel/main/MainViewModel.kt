package com.peranidze.travel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.cache.PreferenceHelper
import org.koin.core.KoinComponent
import org.koin.core.inject

open class MainViewModel : ViewModel(), KoinComponent {

    private val preferenceHelper: PreferenceHelper by inject()
    private val logoutLiveData = MutableLiveData<Boolean>()

    fun getLogoutLiveData() = logoutLiveData

    fun logout() {
        preferenceHelper.clear()
        logoutLiveData.value = preferenceHelper.isUserLoggedIn
    }
}
