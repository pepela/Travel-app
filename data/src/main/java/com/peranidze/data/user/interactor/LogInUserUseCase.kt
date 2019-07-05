package com.peranidze.data.user.interactor

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.interpretator.SingleUseCase
import com.peranidze.data.repository.UserRepository
import com.peranidze.data.user.model.User
import io.reactivex.Single

open class LogInUserUseCase(
    private val userRepository: UserRepository,
    executor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<User, LogInUserUseCase.Params>(executor, postExecutionThread) {

    public override fun buildUseCase(params: Params): Single<User> =
        userRepository.logInUser(params.email, params.password)

    data class Params(val email: String, val password: String)

}
