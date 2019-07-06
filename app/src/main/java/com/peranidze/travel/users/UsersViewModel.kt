package com.peranidze.travel.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.user.interactor.GetUsersUseCase
import io.reactivex.disposables.Disposables

class UsersViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private var usersDisposable = Disposables.empty()

    private val usersLiveData: MutableLiveData<UsersState> = MutableLiveData()

    fun getUserLiveData() = usersLiveData

    fun fetchUsers() {
        usersDisposable.dispose()
        usersLiveData.postValue(UsersState.Loading)
        usersDisposable = getUsersUseCase.execute()
            .subscribe({
                usersLiveData.postValue(UsersState.Success(it))
            }, {
                usersLiveData.postValue(UsersState.Error(it.message))
            })
    }

    override fun onCleared() {
        super.onCleared()
        usersDisposable.dispose()
    }
}
