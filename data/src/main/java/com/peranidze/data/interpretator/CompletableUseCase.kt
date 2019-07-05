package com.peranidze.data.interpretator

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<T, in Params> constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    protected abstract fun buildUseCase(params: Params? = null): Completable

    open fun execute(params: Params? = null): Completable =
        buildUseCase(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)

}
