package com.peranidze.data.trip.interactor

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.repository.TripRepository
import com.peranidze.data.test.factory.DataFactory.Factory.randomDate
import com.peranidze.data.test.factory.DataFactory.Factory.randomLong
import com.peranidze.data.test.factory.DataFactory.Factory.randomUuid
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreateTripUseCaseTest {
    companion object {
        private val USER_ID = randomLong()
        private val DESTINATION = randomUuid()
        private val START_DATE = randomDate()
        private val END_DATE = randomDate()
        private val COMMENT = randomUuid()
    }

    private val tripRepository = mock<TripRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val createTripUseCase = CreateTripUseCase(tripRepository, threadExecutor, postExecutionThread)

    @Test
    fun `createTrip calls repository`() {
        createTripUseCase.buildUseCase(CreateTripUseCase.Params(USER_ID, DESTINATION, START_DATE, END_DATE, COMMENT))

        verify(tripRepository).createTrip(USER_ID, DESTINATION, START_DATE, END_DATE, COMMENT)
    }
}
