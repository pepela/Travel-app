package com.peranidze.travel.trips

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.cache.PreferenceHelper
import com.peranidze.data.trip.interactor.GetTripsUseCase
import com.peranidze.data.trip.model.Trip
import com.peranidze.travel.test.factory.DataFactory.Factory.randomUuid
import com.peranidze.travel.test.factory.trip.TripFactory
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class TripsViewModelTest {

    companion object {
        private val TRIPS = TripFactory.makeTrips(5)
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getTripsUseCase = mock<GetTripsUseCase>()
    private val preferenceHelper = PreferenceHelper(ApplicationProvider.getApplicationContext())

    private val tripsViewModel = TripsViewModel(getTripsUseCase, preferenceHelper)

    @Test
    fun getTripsReturnsSuccess() {
        stubGetTrips(Flowable.just(TRIPS))

        tripsViewModel.fetchTrips()

        assertTrue(tripsViewModel.getTrips().value is TripsState.Success)
    }

    @Test
    fun getTripsReturnsTrips() {
        stubGetTrips(Flowable.just(TRIPS))

        tripsViewModel.fetchTrips()

        assertEquals(tripsViewModel.getTrips().value?.data, TRIPS)
    }

    @Test
    fun getTripsReturnsNoErrorMessage() {
        stubGetTrips(Flowable.just(TRIPS))

        tripsViewModel.fetchTrips()

        assertEquals(tripsViewModel.getTrips().value?.errorMessage, null)
    }

    @Test
    fun getTripsFailureReturnsError() {
        stubGetTrips(Flowable.error(RuntimeException()))

        tripsViewModel.fetchTrips()

        assertTrue(tripsViewModel.getTrips().value is TripsState.Error)
    }

    @Test
    fun getTripsFailureReturnsNoTrips() {
        stubGetTrips(Flowable.error(RuntimeException()))

        tripsViewModel.fetchTrips()

        assertEquals(tripsViewModel.getTrips().value?.data, null)
    }

    @Test
    fun getTripsFailureReturnsErrorMessage() {
        val errorMessage = randomUuid()
        stubGetTrips(Flowable.error(RuntimeException(errorMessage)))

        tripsViewModel.fetchTrips()

        assertEquals(tripsViewModel.getTrips().value?.errorMessage, errorMessage)
    }

    private fun stubGetTrips(flowable: Flowable<List<Trip>>) {
        whenever(getTripsUseCase.execute(any())).thenReturn(flowable)
    }

}
