package com.peranidze.remote.extensions

import com.peranidze.remote.exception.UnauthorizedException
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.HttpException

fun <T> Single<T>.withErrorHandling(): Single<T> =
    this.onErrorResumeNext {
        if (it is HttpException && it.code() == 401) {
            return@onErrorResumeNext Single.error<T>(UnauthorizedException())
        }
        return@onErrorResumeNext Single.error<T>(it)
    }

fun Completable.withErrorHandling(): Completable =
    this.onErrorResumeNext {
        if (it is HttpException && it.code() == 401) {
            return@onErrorResumeNext Completable.error(UnauthorizedException())
        }
        return@onErrorResumeNext Completable.error(it)
    }
