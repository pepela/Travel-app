package com.peranidze.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {

    companion object {

        fun makeRetrofit(isDebug: Boolean, headerInterceptor: HeaderInterceptor): Retrofit =
            Retrofit.Builder()
                .client(makeOkHttpClient(makeLoggingInterceptor(isDebug), headerInterceptor))
                .baseUrl("https://travel-shmevel.herokuapp.com/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(makeGson()))
                .build()

        private fun makeGson(): Gson =
            GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .create()

        private fun makeOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            headerInterceptor: HeaderInterceptor
        ): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

        private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = if (isDebug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
            return logging
        }

    }
}
