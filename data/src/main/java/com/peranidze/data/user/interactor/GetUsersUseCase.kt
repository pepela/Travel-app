package com.peranidze.data.user.interactor

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.interpretator.FlowableNoParamsUseCase
import com.peranidze.data.repository.UserRepository
import com.peranidze.data.user.model.User
import io.reactivex.Flowable

open class GetUsersUseCase(
    private val userRepository: UserRepository,
    executor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableNoParamsUseCase<List<User>>(executor, postExecutionThread) {

    public override fun buildUseCase(): Flowable<List<User>> = userRepository.getUsers()

}
