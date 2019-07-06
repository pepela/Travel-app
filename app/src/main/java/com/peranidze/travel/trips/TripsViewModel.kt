package com.peranidze.travel.trips

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.trip.interactor.GetTripsUseCase
import io.reactivex.disposables.Disposables

class TripsViewModel(private val getTripsUseCase: GetTripsUseCase) : ViewModel() {

    private var tripDisposable = Disposables.empty()
    private val tripsLiveData: MutableLiveData<TripsState> = MutableLiveData()

    public fun getTrips() = tripsLiveData

    public fun fetchTrips(userId: Long) {
        tripDisposable.dispose()
        tripsLiveData.postValue(TripsState.Loading)
        tripDisposable = getTripsUseCase.execute(userId)
            .subscribe({
                tripsLiveData.postValue(TripsState.Success(it))
            }, {
                tripsLiveData.postValue(TripsState.Error(it.message))
            })
    }

    override fun onCleared() {
        super.onCleared()
        tripDisposable.dispose()
    }

}
