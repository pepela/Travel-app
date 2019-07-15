package com.peranidze.data.user.interactor

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.interpretator.FlowableUseCase
import com.peranidze.data.repository.UserRepository
import com.peranidze.data.user.model.User
import io.reactivex.Flowable

open class RegisterUserUseCase(
    private val userRepository: UserRepository,
    executor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<User, RegisterUserUseCase.Params>(executor, postExecutionThread) {

    public override fun buildUseCase(params: Params): Flowable<User> =
        userRepository.register(params.login, params.email, params.password)

    data class Params(val login: String, val email: String, val password: String)

}
