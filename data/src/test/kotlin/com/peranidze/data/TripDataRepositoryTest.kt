package com.peranidze.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.data.source.trip.TripDataStore
import com.peranidze.data.source.trip.TripDataStoreFactory
import com.peranidze.data.test.factory.TripFactory
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TripDataRepositoryTest {

    companion object {
        private const val TRIP_ID = 1L
        private val TRIP = TripFactory.makeTrip()
        private val TRIPS = TripFactory.makeTrips()
    }

    private val remoteDataStore = mock<TripDataStore>()
    private val tripDataStoreFactory = mock<TripDataStoreFactory>()

    private val tripRepository: TripDataRepository = TripDataRepository(tripDataStoreFactory)

    @Before
    fun setup() {
        whenever(tripDataStoreFactory.getDataSource()).thenReturn(remoteDataStore)
        whenever(remoteDataStore.getTrip(any())).thenReturn(Single.just(TRIP))
        whenever(remoteDataStore.getTripsFor(any())).thenReturn(Single.just(TRIPS))
        whenever(remoteDataStore.deleteTrip(any())).thenReturn(Completable.complete())
        whenever(remoteDataStore.updateTrip(any())).thenReturn(Single.just(TRIP))
    }

    @Test
    fun `getTrip returns trip`() {
        val testObserver = tripRepository.getTrip(TRIP_ID).test()
        testObserver.assertValue(TRIP)
    }

    @Test
    fun `getTrips returns trips`() {
        val testObserver = tripRepository.getTripsFor(TRIP_ID).test()
        testObserver.assertValue(TRIPS)
    }

    @Test
    fun `updateTrip returns trip`() {
        val testObserver = tripRepository.updateTrip(TRIP).test()
        testObserver.assertValue(TRIP)
    }

    @Test
    fun `deleteTrip completes`() {
        val testObserver = tripRepository.deleteTrip(TRIP_ID).test()
        testObserver.assertComplete()
    }
}