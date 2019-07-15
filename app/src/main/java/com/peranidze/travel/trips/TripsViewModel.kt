package com.peranidze.travel.trips

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.peranidze.cache.PreferenceHelper
import com.peranidze.data.trip.interactor.GetTripsUseCase
import com.peranidze.remote.exception.UnauthorizedException
import com.peranidze.travel.main.MainViewModel
import io.reactivex.disposables.Disposables

class TripsViewModel(private val getTripsUseCase: GetTripsUseCase, private val preferenceHelper: PreferenceHelper) :
    MainViewModel() {

    private var tripDisposable = Disposables.empty()
    private val tripsLiveData: MutableLiveData<TripsState> = MutableLiveData()

    public fun getTrips() = tripsLiveData

    public fun fetchTrips() {
        tripDisposable.dispose()
        tripsLiveData.postValue(TripsState.Loading)
        tripDisposable = getTripsUseCase.execute(preferenceHelper.userId)
            .subscribe({
                tripsLiveData.postValue(TripsState.Success(it))
            }, {
                Log.e(TripsViewModel::class.java.simpleName, it.toString())
                if (it is UnauthorizedException) {
                    logout()
                } else {
                    tripsLiveData.postValue(TripsState.Error(it.message))
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        tripDisposable.dispose()
    }

}
