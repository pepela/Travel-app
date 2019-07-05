package com.peranidze.travel.signin.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.user.interactor.LogInUserUseCase
import io.reactivex.disposables.Disposables

class LoginViewModel(private val logInUserUseCase: LogInUserUseCase) : ViewModel() {

    private var loginDisposable = Disposables.empty()
    private val loginLiveData: MutableLiveData<LoginState> = MutableLiveData()

    fun getLoginLiveData() = loginLiveData

    fun doLogin(email: String, password: String) {
        loginLiveData.postValue(LoginState.Loading)
        loginDisposable.dispose()
        loginDisposable = logInUserUseCase
                .execute(LogInUserUseCase.Params(email, password))
                .subscribe({
                    loginLiveData.postValue(LoginState.Success(it))
                }, {
                    loginLiveData.postValue(LoginState.Error(it.message))
                })

    }

    override fun onCleared() {
        super.onCleared()
        loginDisposable.dispose()
    }
}
