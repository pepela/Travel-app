package com.peranidze.data.trip.interactor

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.repository.TripRepository
import com.peranidze.data.test.factory.DataFactory.Factory.randomLong
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class UpdateTripUseCaseTest {
    companion object {
        private val TRIP_ID = randomLong()
    }

    private val tripRepository = mock<TripRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val deleteTripUseCase = DeleteTripUseCase(tripRepository, threadExecutor, postExecutionThread)

    @Test
    fun `deleteTrip calls repository`() {
        deleteTripUseCase.buildUseCase(TRIP_ID)

        verify(tripRepository).deleteTrip(TRIP_ID)
    }
}
