package com.peranidze.travel.trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import io.reactivex.disposables.Disposables
import io.reactivex.functions.BiFunction
import java.util.*

class TripViewModel(
    private val getTripUseCase: GetTripUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
    private val updateTripUseCase: UpdateTripUseCase,
    private val createTripUseCase: CreateTripUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private var getTripDisposable = Disposables.empty()
    private var getTripAndUsersDisposable = Disposables.empty()
    private var getUsersDisposable = Disposables.empty()
    private var createTripDisposable = Disposables.empty()
    private var updateTripDisposable = Disposables.empty()
    private var deleteTripDisposable = Disposables.empty()

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
        getTripDisposable.dispose()
        tripLiveData.postValue(TripState.Loading)
        getTripDisposable = getTripUseCase.execute(id)
            .subscribe({
                tripLiveData.postValue(TripState.Success(it))
            }, {
                tripLiveData.postValue(TripState.Error(it.message))
            })
        disposables.add(getTripDisposable)
    }

    fun fetchTripAndUsers(id: Long) {
        getTripAndUsersDisposable.dispose()
        tripAndUsersLiveData.postValue(TripAndUsersState.Loading)
        getTripAndUsersDisposable = Flowable.zip(getTripUseCase.execute(id), getUsersUseCase.execute(),
            BiFunction { trip: Trip, users: List<User> -> Pair(trip, users) })
            .subscribe({
                tripAndUsersLiveData.postValue(TripAndUsersState.Success(it))
            }, {
                tripAndUsersLiveData.postValue(TripAndUsersState.Error(it.message))
            })
        disposables.add(getTripAndUsersDisposable)
    }

    fun fetchUsers() {
        getUsersDisposable.dispose()
        usersLiveData.postValue(UsersState.Loading)
        getUsersDisposable = getUsersUseCase.execute()
            .subscribe({
                usersLiveData.postValue(UsersState.Success(it))
            }, {
                usersLiveData.postValue(UsersState.Error(it.message))
            })
        disposables.add(getUsersDisposable)
    }

    fun deleteTrip(id: Long) {
        deleteTripDisposable.dispose()
        deleteTripLiveData.postValue(TripState.Loading)
        deleteTripDisposable = deleteTripUseCase.execute(id)
            .subscribe({
                deleteTripLiveData.postValue(TripState.Success())
            }, {
                deleteTripLiveData.postValue(TripState.Error(it.message))
            })
        disposables.add(deleteTripDisposable)
    }

    fun updateTrip(trip: Trip) {
        updateTripDisposable.dispose()
        updateTripLiveData.postValue(TripState.Loading)
        updateTripDisposable = updateTripUseCase.execute(trip)
            .subscribe({
                updateTripLiveData.postValue(TripState.Success(it))
            }, {
                updateTripLiveData.postValue(TripState.Error(it.message))
            })
        disposables.add(updateTripDisposable)
    }

    fun createTrip(
        destination: String,
        startDate: Date,
        endDate: Date,
        comment: String?,
        user: User? = null
    ) {
        createTripDisposable.dispose()
        createTripLiveData.postValue(TripState.Loading)
        createTripDisposable = createTripUseCase
            .execute(CreateTripUseCase.Params(destination, startDate, endDate, comment, user))
            .subscribe({
                createTripLiveData.postValue(TripState.Success(it))
            }, {
                createTripLiveData.postValue(TripState.Error(it.message))
            })
        disposables.add(createTripDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}
