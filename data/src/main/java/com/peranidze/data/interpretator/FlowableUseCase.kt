package com.peranidze.data.interpretator

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

abstract class FlowableUseCase<T, in Params> constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    protected abstract fun buildUseCase(params: Params? = null): Flowable<T>

    open fun execute(params: Params? = null): Flowable<T> =
        buildUseCase(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)

}
