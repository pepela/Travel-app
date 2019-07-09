package com.peranidze.travel.trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.trip.interactor.DeleteTripUseCase
import com.peranidze.data.trip.interactor.GetTripUseCase
import com.peranidze.data.trip.interactor.UpdateTripUseCase
import com.peranidze.data.trip.model.Trip
import com.peranidze.data.user.interactor.GetUsersUseCase
import com.peranidze.data.user.model.User
import com.peranidze.travel.users.UsersState
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction

class TripViewModel(
    private val getTripUseCase: GetTripUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
    private val updateTripUseCase: UpdateTripUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private var tripLiveData: MutableLiveData<TripState> = MutableLiveData()
    private var tripAndUsersLiveData: MutableLiveData<TripAndUsersState> = MutableLiveData()
    private var usersLiveData: MutableLiveData<UsersState> = MutableLiveData()

    fun getTripLiveData() = tripLiveData
    fun getTripAndUsersLiveData() = tripAndUsersLiveData
    fun getUsersLiveData() = usersLiveData

    fun fetchTrip(id: Long) {
        tripLiveData.postValue(TripState.Loading)
        disposables.add(
            getTripUseCase.execute(id)
                .subscribe({
                    tripLiveData.postValue(TripState.Success(it))
                }, {
                    tripLiveData.postValue(TripState.Error(it.message))
                })
        )
    }

    fun fetchTripAndUsers(id: Long) {
        tripAndUsersLiveData.postValue(TripAndUsersState.Loading)
        disposables.add(
            Flowable.zip(
                getTripUseCase.execute(id),
                getUsersUseCase.execute(),
                BiFunction { trip: Trip, users: List<User> -> Pair(trip, users) })
                .subscribe({
                    tripAndUsersLiveData.postValue(TripAndUsersState.Success(it))
                }, {
                    tripAndUsersLiveData.postValue(TripAndUsersState.Error(it.message))
                })
        )
    }

    fun fetchUsers() {
        usersLiveData.postValue(UsersState.Loading)
        disposables.add(
            getUsersUseCase.execute()
                .subscribe({
                    usersLiveData.postValue(UsersState.Success(it))
                }, {
                    usersLiveData.postValue(UsersState.Error(it.message))
                })
        )
    }

    fun deleteTrip(id: Long) {
        usersLiveData.postValue(UsersState.Loading)
        disposables.add(
            deleteTripUseCase.execute(id)
                .subscribe({

                }, {

                })
        )
    }

    fun updateTrip(trip: Trip) {
        usersLiveData.postValue(UsersState.Loading)
        disposables.add(
            updateTripUseCase.execute(trip)
                .subscribe({

                }, {

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}
