package com.peranidze.data.user.interactor

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.interpretator.FlowableUseCase
import com.peranidze.data.repository.UserRepository
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import io.reactivex.Flowable

open class CreateUserUseCase(
    private val userRepository: UserRepository,
    executor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<User, CreateUserUseCase.Params>(executor, postExecutionThread) {

    public override fun buildUseCase(params: Params): Flowable<User> =
        userRepository.createUser(params.login, params.email, params.roles)

    data class Params(val login: String, val email: String, val roles: List<Role>)
}
