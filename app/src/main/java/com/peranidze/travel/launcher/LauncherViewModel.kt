package com.peranidze.travel.launcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.cache.PreferenceHelper

class LauncherViewModel(private val preferenceHelper: PreferenceHelper) : ViewModel() {

    private val userLoggedInLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getUserIsLoggedInLiveData(): MutableLiveData<Boolean> = userLoggedInLiveData

    fun checkUserIsLoggedIn() {
        userLoggedInLiveData.value = preferenceHelper.isUserLoggedIn
    }

}
