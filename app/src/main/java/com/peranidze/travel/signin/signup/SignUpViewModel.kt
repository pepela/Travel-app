package com.peranidze.travel.signin.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.data.user.interactor.RegisterUserUseCase
import io.reactivex.disposables.CompositeDisposable

class SignUpViewModel(private val registerUserUseCase: RegisterUserUseCase) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val registerUserLiveData: MutableLiveData<SignUpState> = MutableLiveData()

    fun getRegisterUserLiveData() = registerUserLiveData

    fun registerUser(login: String, email: String, password: String) {
        registerUserLiveData.postValue(SignUpState.Loading)
        disposables.add(
            registerUserUseCase
                .execute(RegisterUserUseCase.Params(login, email, password))
                .subscribe({
                    registerUserLiveData.postValue(SignUpState.Success(it))
                }, {
                    registerUserLiveData.postValue(SignUpState.Error(it.message))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
