package com.peranidze.data.trip.interactor

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.interpretator.SingleUseCase
import com.peranidze.data.repository.TripRepository
import com.peranidze.data.trip.model.Trip
import io.reactivex.Single

open class GetTripsUseCase(
    private val tripRepository: TripRepository,
    executor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Trip>, Long>(executor, postExecutionThread) {

    public override fun buildUseCase(params: Long): Single<List<Trip>> =
        tripRepository.getTripsFor(params)

}
