package com.peranidze.data.trip.interactor

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.repository.TripRepository
import com.peranidze.data.test.factory.TripFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class DeleteTripUseCaseTest {
    companion object {
        private val TRIP = TripFactory.makeTrip()
    }

    private val tripRepository = mock<TripRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val updateTripUseCase = UpdateTripUseCase(tripRepository, threadExecutor, postExecutionThread)

    @Test
    fun `updateTrip calls repository`() {
        updateTripUseCase.buildUseCase(TRIP)

        verify(tripRepository).updateTrip(TRIP)
    }
}
