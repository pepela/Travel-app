package com.peranidze.data.trip.interactor

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.interpretator.FlowableUseCase
import com.peranidze.data.repository.TripRepository
import com.peranidze.data.trip.model.Trip
import com.peranidze.data.user.model.User
import io.reactivex.Flowable
import java.util.*

open class CreateTripUseCase(
    private val tripRepository: TripRepository,
    executor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<Trip, CreateTripUseCase.Params>(executor, postExecutionThread) {

    public override fun buildUseCase(params: Params): Flowable<Trip> = with(params) {
        tripRepository.createTrip(destination, startDate, endDate, comment, user)
    }

    data class Params(
        val destination: String,
        val startDate: Date,
        val endDate: Date,
        val comment: String?,
        val user: User? = null
    )

}
