package com.peranidze.travel

import android.app.Application
import com.peranidze.travel.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TravelApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@TravelApplication)
            androidLogger()
            modules(
                listOf(
                    applicationModule,
                    launcherModule,
                    loginModule,
                    signUpModule,
                    usersModule,
                    userModule,
                    tripsModule,
                    tripModule,
                    mainModule
                )
            )
        }
    }

}
