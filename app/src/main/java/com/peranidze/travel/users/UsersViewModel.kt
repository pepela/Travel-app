package com.peranidze.travel.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.user.interactor.GetUsersUseCase
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import io.reactivex.Flowable
import io.reactivex.disposables.Disposables
import java.util.concurrent.TimeUnit

class UsersViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private var usersDisposable = Disposables.empty()

    private val usersLiveData: MutableLiveData<UsersState> = MutableLiveData()

    fun getUserLiveData() = usersLiveData

    fun fetchUsers() {
        usersDisposable.dispose()
        usersLiveData.postValue(UsersState.Loading)
//        usersDisposable = getUsersUseCase.execute()
//            .subscribe({
//                usersLiveData.postValue(UsersState.Success(it))
//            }, {
//                usersLiveData.postValue(UsersState.Error(it.message))
//            })

        val yle = Flowable.timer(4, TimeUnit.SECONDS).subscribe {
            usersLiveData.postValue(
                UsersState.Success(
                    listOf(
                        User(1L, "giorgi@test.ge", Role.MANAGER),
                        User(2L, "mutela@test.ge", Role.REGULAR),
                        User(3L, "yleqala@test.ge", Role.ADMIN)
                    )
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        usersDisposable.dispose()
    }
}
