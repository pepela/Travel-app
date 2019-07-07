package com.peranidze.travel.user

import androidx.lifecycle.ViewModel
import com.peranidze.data.user.interactor.GetUserUseCase
import io.reactivex.disposables.Disposables

class UserViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    private var getUserDisposable = Disposables.empty()

    override fun onCleared() {
        super.onCleared()
        getUserDisposable.dispose()
    }
}
