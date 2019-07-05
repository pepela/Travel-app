package com.peranidze.data.source.trip

open class TripDataStoreFactory(private val remote: TripDataStore) {

    fun getDataSource(): TripDataStore = remote

}
