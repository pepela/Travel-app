package com.peranidze.travel.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.user.interactor.GetUserUseCase
import io.reactivex.disposables.Disposables

class UserViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    private var getUserDisposable = Disposables.empty()
    private val getUserLiveData = MutableLiveData<UserState>()

    fun getUserLiveData() = getUserLiveData

    fun fetchUser(id: Long) {
        getUserDisposable.dispose()
        getUserLiveData.postValue(UserState.Loading)
        getUserDisposable = getUserUseCase.execute(id)
            .subscribe({
                getUserLiveData.postValue(UserState.Success(it))
            }, {
                getUserLiveData.postValue(UserState.Error(it.message))
            })
    }

    override fun onCleared() {
        super.onCleared()
        getUserDisposable.dispose()
    }
}
