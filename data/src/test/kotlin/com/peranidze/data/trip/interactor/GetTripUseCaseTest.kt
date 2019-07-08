package com.peranidze.data.trip.interactor

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.repository.TripRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetTripUseCaseTest {

    companion object {
        private const val TRIP_ID = 1L
    }

    private val tripRepository = mock<TripRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val getTripUseCase = GetTripUseCase(tripRepository, threadExecutor, postExecutionThread)

    @Test
    fun `getTripUseCase calls repository`() {
        getTripUseCase.buildUseCase(TRIP_ID)

        verify(tripRepository).getTrip(TRIP_ID)
    }
}