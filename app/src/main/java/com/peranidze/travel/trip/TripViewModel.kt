package com.peranidze.travel.trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.trip.interactor.GetTripUseCase
import com.peranidze.data.trip.model.Trip
import com.peranidze.data.user.interactor.GetUsersUseCase
import com.peranidze.data.user.model.User
import io.reactivex.Single
import io.reactivex.disposables.Disposables
import io.reactivex.functions.BiFunction

class TripViewModel(
    private val getTripUseCase: GetTripUseCase,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private var disposable = Disposables.empty()
    private var tripLiveData: MutableLiveData<TripState> = MutableLiveData()
    private var tripAndUsersLiveData: MutableLiveData<TripAndUsersState> = MutableLiveData()

    fun getTripLiveData() = tripLiveData
    fun getTripAndUsersLiveData() = tripAndUsersLiveData

    fun fetchTrip(id: Long) {
        disposable.dispose()
        tripLiveData.postValue(TripState.Loading)
        disposable = getTripUseCase.execute(id)
            .subscribe({
                tripLiveData.postValue(TripState.Success(it))
            }, {
                tripLiveData.postValue(TripState.Error(it.message))
            })
    }

    fun fetchTripAndUsers(id: Long) {
        disposable.dispose()
        tripAndUsersLiveData.postValue(TripAndUsersState.Loading)
        disposable =
            Single.zip(
                getTripUseCase.execute(id),
                getUsersUseCase.execute(),
                BiFunction { trip: Trip, users: List<User> -> Pair(trip, users) })
                .subscribe({
                    tripAndUsersLiveData.postValue(TripAndUsersState.Success(it))
                }, {
                    tripAndUsersLiveData.postValue(TripAndUsersState.Error(it.message))
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}
