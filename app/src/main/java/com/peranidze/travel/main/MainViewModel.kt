package com.peranidze.travel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.cache.PreferenceHelper

class MainViewModel(private val preferenceHelper: PreferenceHelper) : ViewModel() {

    private val logoutLiveData = MutableLiveData<Boolean>()

    fun getLogoutLiveData() = logoutLiveData

    fun logout() {
        preferenceHelper.clear()
        logoutLiveData.value = preferenceHelper.isUserLoggedIn
    }
}
