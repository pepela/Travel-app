package com.peranidze.travel.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.user.interactor.DeleteUserUseCase
import com.peranidze.data.user.interactor.GetUserUseCase
import com.peranidze.data.user.interactor.UpdateUserUseCase
import com.peranidze.data.user.model.User
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables

class UserViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private var getUserDisposable = Disposables.empty()
    private var updateUserDisposable = Disposables.empty()
    private var deleteUserDisposable = Disposables.empty()
    private val userLiveData = MutableLiveData<UserState>()

    fun getUserLiveData() = userLiveData

    fun fetchUser(id: Long) {
        getUserDisposable.dispose()
        userLiveData.postValue(UserState.Loading)
        getUserDisposable = getUserUseCase.execute(id)
            .subscribe({
                userLiveData.postValue(UserState.Success(it))
            }, {
                userLiveData.postValue(UserState.Error(it.message))
            })
        disposables.add(getUserDisposable)
    }

    fun updateUser(user: User) {
        updateUserDisposable.dispose()
        userLiveData.postValue(UserState.Loading)
        updateUserDisposable = updateUserUseCase.execute(user)
            .subscribe({
                userLiveData.postValue(UserState.Success(it))
            }, {
                userLiveData.postValue(UserState.Error(it.message))
            })
        disposables.add(updateUserDisposable)
    }

    fun deleteUser(id: Long) {
        deleteUserDisposable.dispose()
        userLiveData.postValue(UserState.Loading)
        deleteUserDisposable = deleteUserUseCase.execute(id)
            .subscribe({
                userLiveData.postValue(UserState.Success())
            }, {
                userLiveData.postValue(UserState.Error(it.message))
            })
        disposables.add(deleteUserDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
