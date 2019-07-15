package com.peranidze.travel.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.user.interactor.CreateUserUseCase
import com.peranidze.data.user.interactor.DeleteUserUseCase
import com.peranidze.data.user.interactor.GetUserUseCase
import com.peranidze.data.user.interactor.UpdateUserUseCase
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables

class UserViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private var getUserDisposable = Disposables.empty()
    private var updateUserDisposable = Disposables.empty()
    private var deleteUserDisposable = Disposables.empty()
    private var createUserDisposable = Disposables.empty()

    private val getUserLiveData = MutableLiveData<GetUserState>()
    private val createUserLiveData = MutableLiveData<CreateUserState>()
    private val updateUserLiveData = MutableLiveData<UpdateUserState>()
    private val deleteUserLiveData = MutableLiveData<DeleteUserState>()

    fun getUserLiveData() = getUserLiveData
    fun createUserLiveData() = createUserLiveData
    fun updateUserLiveData() = updateUserLiveData
    fun deleteUserLiveData() = deleteUserLiveData

    fun fetchUser(login: String) {
        getUserDisposable.dispose()
        getUserLiveData.postValue(GetUserState.Loading)
        getUserDisposable = getUserUseCase.execute(login)
            .subscribe({
                getUserLiveData.postValue(GetUserState.Success(it))
            }, {
                getUserLiveData.postValue(GetUserState.Error(it.message))
            })
        disposables.add(getUserDisposable)
    }

    fun createUser(login: String, email: String, roles: List<Role>) {
        createUserDisposable.dispose()
        createUserLiveData.postValue(CreateUserState.Loading)
        createUserDisposable = createUserUseCase.execute(CreateUserUseCase.Params(login, email, roles))
            .subscribe({
                createUserLiveData.postValue(CreateUserState.Success(it))
            }, {
                createUserLiveData.postValue(CreateUserState.Error(it.message))
            })
        disposables.add(createUserDisposable)
    }

    fun updateUser(user: User) {
        updateUserDisposable.dispose()
        updateUserLiveData.postValue(UpdateUserState.Loading)
        updateUserDisposable = updateUserUseCase.execute(user)
            .subscribe({
                updateUserLiveData.postValue(UpdateUserState.Success(it))
            }, {
                updateUserLiveData.postValue(UpdateUserState.Error(it.message))
            })
        disposables.add(updateUserDisposable)
    }

    fun deleteUser(login: String) {
        deleteUserDisposable.dispose()
        deleteUserLiveData.postValue(DeleteUserState.Loading)
        deleteUserDisposable = deleteUserUseCase.execute(login)
            .subscribe({
                deleteUserLiveData.postValue(DeleteUserState.Success())
            }, {
                deleteUserLiveData.postValue(DeleteUserState.Error(it.message))
            })
        disposables.add(deleteUserDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
