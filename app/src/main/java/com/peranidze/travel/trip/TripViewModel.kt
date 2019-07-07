package com.peranidze.travel.trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.trip.interactor.GetTripUseCase
import io.reactivex.disposables.Disposables

class TripViewModel(private val getTripUseCase: GetTripUseCase) : ViewModel() {

    private var getTripDisposable = Disposables.empty()
    private var tripLiveData: MutableLiveData<TripState> = MutableLiveData()

    fun getTripLiveData() = tripLiveData

    fun fetchTrip(id: Long) {
        tripLiveData.postValue(TripState.Loading)
        getTripDisposable = getTripUseCase.execute(id)
            .subscribe({
                tripLiveData.postValue(TripState.Success(it))
            }, {
                tripLiveData.postValue(TripState.Error(it.message))
            })
    }

    override fun onCleared() {
        super.onCleared()
        getTripDisposable.dispose()
    }

}
