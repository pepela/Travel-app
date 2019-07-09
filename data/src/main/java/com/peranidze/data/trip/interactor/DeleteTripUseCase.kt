package com.peranidze.data.trip.interactor

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.interpretator.CompletableUseCase
import com.peranidze.data.repository.TripRepository
import io.reactivex.Completable

open class DeleteTripUseCase(
    private val tripRepository: TripRepository,
    executor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<Long>(executor, postExecutionThread) {

    public override fun buildUseCase(params: Long): Completable = tripRepository.deleteTrip(params)
}