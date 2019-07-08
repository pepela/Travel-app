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
class GetTripsUseCaseTest {

    companion object {
        private const val USER_ID = 1L
    }

    private val tripRepository = mock<TripRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val getTripsUseCase = GetTripsUseCase(tripRepository, threadExecutor, postExecutionThread)

    @Test
    fun `getTripsUseCase calls repository`() {
        getTripsUseCase.buildUseCase(USER_ID)

        verify(tripRepository).getTripsFor(USER_ID)
    }
}