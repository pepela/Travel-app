package com.peranidze.remote.trip

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.remote.trip.mapper.TripMapper
import com.peranidze.test.trip.TripModelFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TripRemoteDataStoreImplTest {

    companion object {
        private const val USER_ID = 1L
        private val TRIPS = TripModelFactory.makeTripModels()
    }

    private val tripService = mock<TripService>()
    private val tripMapper = mock<TripMapper>()

    private val tripRemoteDataStoreImpl = TripRemoteDataStoreImpl(tripService, tripMapper)

    @Before
    fun setup() {
        whenever(tripService.getTrips()).thenReturn(Single.just(TRIPS))
    }

    @Test
    fun `getTripsFor returns trips`() {
        val testSubscriber = tripRemoteDataStoreImpl.getTripsFor(USER_ID).test()
        testSubscriber.assertValue(tripMapper.from(TRIPS))
    }

}
