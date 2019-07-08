package com.peranidze.data.source.trip

open class TripDataStoreFactory(private val remote: TripDataStore) {

    open fun getDataSource(): TripDataStore = remote

}
