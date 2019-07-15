package com.peranidze.remote

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    private var sessionToken: String? = null

    fun setSessionToken(sessionToken: String?) {
        this.sessionToken = sessionToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()

        if (request.header("") == null) {
            if (sessionToken != null) {
                requestBuilder.addHeader("Authorization", "Bearer $sessionToken!!")
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}
