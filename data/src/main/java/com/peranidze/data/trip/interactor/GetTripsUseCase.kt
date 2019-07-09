package com.peranidze.data.trip.interactor

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.interpretator.FlowableUseCase
import com.peranidze.data.repository.TripRepository
import com.peranidze.data.trip.model.Trip
import io.reactivex.Flowable

open class GetTripsUseCase(
    private val tripRepository: TripRepository,
    executor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<Trip>, Long>(executor, postExecutionThread) {

    public override fun buildUseCase(params: Long): Flowable<List<Trip>> = tripRepository.getTripsFor(params)

}
