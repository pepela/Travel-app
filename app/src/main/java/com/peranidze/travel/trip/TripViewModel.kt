package com.peranidze.travel.trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.cache.PreferenceHelper
import com.peranidze.data.trip.interactor.CreateTripUseCase
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
import java.util.*

class TripViewModel(
    private val getTripUseCase: GetTripUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
    private val updateTripUseCase: UpdateTripUseCase,
    private val createTripUseCase: CreateTripUseCase,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private var tripLiveData: MutableLiveData<TripState> = MutableLiveData()
    private var tripAndUsersLiveData: MutableLiveData<TripAndUsersState> = MutableLiveData()
    private var usersLiveData: MutableLiveData<UsersState> = MutableLiveData()
    private var createTripLiveData: MutableLiveData<TripState> = MutableLiveData()
    private var updateTripLiveData: MutableLiveData<TripState> = MutableLiveData()
    private var deleteTripLiveData: MutableLiveData<TripState> = MutableLiveData()

    fun getTripLiveData() = tripLiveData
    fun getTripAndUsersLiveData() = tripAndUsersLiveData
    fun getUsersLiveData() = usersLiveData
    fun getCreateTripLiveData() = createTripLiveData
    fun getUpdateTripLiveData() = updateTripLiveData
    fun deleteUpdateTripLiveData() = deleteTripLiveData

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
        deleteTripLiveData.postValue(TripState.Loading)
        disposables.add(
            deleteTripUseCase.execute(id)
                .subscribe({
                    deleteTripLiveData.postValue(TripState.Success())
                }, {
                    deleteTripLiveData.postValue(TripState.Error(it.message))
                })
        )
    }

    fun updateTrip(trip: Trip) {
        updateTripLiveData.postValue(TripState.Loading)
        disposables.add(
            updateTripUseCase.execute(trip)
                .subscribe({
                    updateTripLiveData.postValue(TripState.Success(it))
                }, {
                    updateTripLiveData.postValue(TripState.Error(it.message))
                })
        )
    }


//    fun updateTrip(tripId: Long, destination: String, startDate: Long, endDate: Long, comment: String?) {
//        usersLiveData.postValue(UsersState.Loading)
//        disposables.add(
//            updateTripUseCase.execute(Trip())
//                .subscribe({
//
//                }, {
//
//                })
//        )
//    }

    fun createTrip(userId: Long?, destination: String, startDate: Date, endDate: Date, comment: String?) {
        createTripLiveData.postValue(TripState.Loading)
        disposables.add(
            createTripUseCase.execute(
                CreateTripUseCase.Params(
                    userId ?: preferenceHelper.userId,
                    destination,
                    startDate,
                    endDate,
                    comment
                )
            )
                .subscribe({
                    createTripLiveData.postValue(TripState.Success(it))
                }, {
                    createTripLiveData.postValue(TripState.Error(it.message))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}
