package com.peranidze.remote.trip

import com.peranidze.remote.trip.model.TripModel
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface TripService {

    @GET("trips/{id}")
    fun getTrip(@Path("id") id: Long): Single<TripModel>

    @GET("trips/")
    fun getTrips(): Single<List<TripModel>>

    @PUT("trips/{id}")
    fun updateTrip(@Path("id") id: Long, @Body tripModel: TripModel): Single<TripModel>

    @DELETE("trips/{id}")
    fun deleteTrip(@Path("id") id: Long): Completable

}
