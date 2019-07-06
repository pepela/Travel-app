package com.peranidze.travel

import android.app.Application
import com.peranidze.travel.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TravelApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@TravelApplication)

            modules(
                listOf(
                    applicationModule, launcherModule, loginModule, signUpModule, usersModule, tripsModule, mainModule
                )
            )
        }
    }

}
