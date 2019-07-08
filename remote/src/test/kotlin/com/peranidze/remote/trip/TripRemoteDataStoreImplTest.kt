package com.peranidze.remote.trip

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.data.trip.model.Trip
import com.peranidze.remote.trip.mapper.TripMapper
import com.peranidze.test.DataFactory.Factory.randomDate
import com.peranidze.test.DataFactory.Factory.randomLong
import com.peranidze.test.DataFactory.Factory.randomUuid
import com.peranidze.test.trip.TripModelFactory
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TripRemoteDataStoreImplTest {

    companion object {
        private const val TRIP_ID = 1L
        private const val USER_ID = 1L
        private val TRIP = Trip(randomLong(), randomUuid(), randomDate(), randomDate(), randomUuid())
        private val TRIP_MODEL = TripModelFactory.makeTripModel()
        private val TRIPS = TripModelFactory.makeTripModels()
    }

    private val tripService = mock<TripService>()
    private val tripMapper = mock<TripMapper>()

    private val tripRemoteDataStoreImpl = TripRemoteDataStoreImpl(tripService, tripMapper)

    @Before
    fun setup() {
        whenever(tripService.getTrips()).thenReturn(Single.just(TRIPS))
        whenever(tripService.getTrip(any())).thenReturn(Single.just(TRIP_MODEL))
        whenever(tripService.deleteTrip(any())).thenReturn(Completable.complete())
        whenever(tripService.updateTrip(any(), any())).thenReturn(Single.just(TRIP_MODEL))
        whenever(tripMapper.from(TRIP_MODEL)).thenReturn(TRIP)
        whenever(tripMapper.toModel(TRIP)).thenReturn(TRIP_MODEL)
    }

    @Test
    fun `getTrip returns trip`() {
        val testSubscriber = tripRemoteDataStoreImpl.getTrip(TRIP_ID).test()
        testSubscriber.assertValue(tripMapper.from(TRIP_MODEL))
    }

    @Test
    fun `getTripsFor returns trips`() {
        val testSubscriber = tripRemoteDataStoreImpl.getTripsFor(USER_ID).test()
        testSubscriber.assertValue(tripMapper.from(TRIPS))
    }

    @Test
    fun `updateTrip returns trips`() {
        val testSubscriber = tripRemoteDataStoreImpl.updateTrip(tripMapper.from(TRIP_MODEL)).test()
        testSubscriber.assertValue(tripMapper.from(TRIP_MODEL))
    }

    @Test
    fun `deleteTrip completes`() {
        val testSubscriber = tripRemoteDataStoreImpl.deleteTrip(TRIP_ID).test()
        testSubscriber.assertComplete()
    }
}
