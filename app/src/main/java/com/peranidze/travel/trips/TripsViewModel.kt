package com.peranidze.travel.trips

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.trip.interactor.GetTripsUseCase
import com.peranidze.data.trip.model.Trip
import io.reactivex.Flowable
import io.reactivex.disposables.Disposables
import java.util.*
import java.util.concurrent.TimeUnit

class TripsViewModel(private val getTripsUseCase: GetTripsUseCase) : ViewModel() {

    private var tripDisposable = Disposables.empty()
    private val tripsLiveData: MutableLiveData<TripsState> = MutableLiveData()

    public fun getTrips() = tripsLiveData

    public fun fetchTrips(userId: Long) {
        tripDisposable.dispose()
        tripsLiveData.postValue(TripsState.Loading)
//        tripDisposable = getTripsUseCase.execute(userId)
//            .subscribe({
//                tripsLiveData.postValue(TripsState.Success(it))
//            }, {
//                tripsLiveData.postValue(TripsState.Error(it.message))
//            })

        val yle = Flowable.timer(3, TimeUnit.SECONDS).subscribe {
            tripsLiveData.postValue(
                TripsState.Success(
                    listOf(
                        Trip(1L, "destination1", Date(), Date()),
                        Trip(2L, "destination2", Date(), Date()),
                        Trip(3L, "destination3", Date(), Date()),
                        Trip(4L, "destination4", Date(), Date()),
                        Trip(5L, "destination5", Date(), Date())
                    )
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        tripDisposable.dispose()
    }

}
