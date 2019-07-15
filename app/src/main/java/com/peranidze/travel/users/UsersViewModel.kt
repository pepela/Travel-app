package com.peranidze.travel.users

import androidx.lifecycle.MutableLiveData
import com.peranidze.data.user.interactor.GetUsersUseCase
import com.peranidze.remote.exception.UnauthorizedException
import com.peranidze.travel.main.MainViewModel
import io.reactivex.disposables.Disposables

class UsersViewModel(private val getUsersUseCase: GetUsersUseCase) : MainViewModel() {

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
                if (it is UnauthorizedException) {
                    logout()
                } else {
                    usersLiveData.postValue(UsersState.Error(it.message))
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        usersDisposable.dispose()
    }
}
