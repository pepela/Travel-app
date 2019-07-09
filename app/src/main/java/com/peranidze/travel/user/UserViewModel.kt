package com.peranidze.travel.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.user.interactor.DeleteUserUseCase
import com.peranidze.data.user.interactor.GetUserUseCase
import com.peranidze.data.user.interactor.UpdateUserUseCase
import com.peranidze.data.user.model.User
import io.reactivex.disposables.CompositeDisposable

class UserViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val userLiveData = MutableLiveData<UserState>()

    fun getUserLiveData() = userLiveData

    fun fetchUser(id: Long) {
        disposables.dispose()
        userLiveData.postValue(UserState.Loading)
        disposables.add(
            getUserUseCase.execute(id)
                .subscribe({
                    userLiveData.postValue(UserState.Success(it))
                }, {
                    userLiveData.postValue(UserState.Error(it.message))
                })
        )
    }

    fun updateUser(user: User) {
        disposables.dispose()
        userLiveData.postValue(UserState.Loading)
        disposables.add(
            updateUserUseCase.execute(user)
                .subscribe({
                    userLiveData.postValue(UserState.Success(it))
                }, {
                    userLiveData.postValue(UserState.Error(it.message))
                })
        )
    }

    fun deleteUser(id: Long) {
        disposables.dispose()
        userLiveData.postValue(UserState.Loading)
        disposables.add(
            deleteUserUseCase.execute(id)
                .subscribe({
                    userLiveData.postValue(UserState.Success())
                }, {
                    userLiveData.postValue(UserState.Error(it.message))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
