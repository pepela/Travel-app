package com.peranidze.travel.di

import com.peranidze.cache.PreferenceHelper
import com.peranidze.data.TripDataRepository
import com.peranidze.data.UserDataRepository
import com.peranidze.data.executor.JobExecutor
import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.repository.TripRepository
import com.peranidze.data.repository.UserRepository
import com.peranidze.data.source.trip.TripDataStore
import com.peranidze.data.source.trip.TripDataStoreFactory
import com.peranidze.data.source.user.UserDataStore
import com.peranidze.data.source.user.UserDataStoreFactory
import com.peranidze.data.trip.interactor.GetTripsUseCase
import com.peranidze.data.user.interactor.GetUsersUseCase
import com.peranidze.data.user.interactor.LogInUserUseCase
import com.peranidze.data.user.interactor.SignUpUserUseCase
import com.peranidze.remote.trip.TripRemoteDataStoreImpl
import com.peranidze.remote.trip.TripServiceFactory
import com.peranidze.remote.trip.mapper.TripMapper
import com.peranidze.remote.user.UserRemoteDataStoreImpl
import com.peranidze.remote.user.UserServiceFactory
import com.peranidze.remote.user.mapper.RoleMapper
import com.peranidze.remote.user.mapper.UserMapper
import com.peranidze.travel.BuildConfig
import com.peranidze.travel.UiThread
import com.peranidze.travel.launcher.LauncherViewModel
import com.peranidze.travel.signin.login.LoginViewModel
import com.peranidze.travel.signin.signup.SignupViewModel
import com.peranidze.travel.trips.TripsAdapter
import com.peranidze.travel.trips.TripsViewModel
import com.peranidze.travel.users.UsersAdapter
import com.peranidze.travel.users.UsersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module(override = true) {

    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }

    single { PreferenceHelper(androidContext()) }

    // Users
    single { UserDataStoreFactory(get()) }
    single<UserRepository> { UserDataRepository(get()) }
    single<UserDataStore> { UserRemoteDataStoreImpl(get(), get()) }
    single { UserServiceFactory.makeUserServiceFactory(BuildConfig.DEBUG) }

    // Trips
    single { TripDataStoreFactory(get()) }
    single<TripRepository> { TripDataRepository(get()) }
    single<TripDataStore> { TripRemoteDataStoreImpl(get(), get()) }
    single { TripServiceFactory.makeTripService(BuildConfig.DEBUG) }

    // Mappers
    single { RoleMapper() }
    single { TripMapper() }
    single { UserMapper(get()) }
}

val launcherModule = module {
    viewModel { LauncherViewModel(get()) }
}

val loginModule = module {
    single { LogInUserUseCase(get(), get(), get()) }
    viewModel { LoginViewModel(get()) }
}

val signUpModule = module {
    single { SignUpUserUseCase(get(), get(), get()) }
    viewModel { SignupViewModel(get()) }
}

val usersModule = module {
    single { GetUsersUseCase(get(), get(), get()) }
    single { UsersAdapter() }
    viewModel { UsersViewModel(get()) }
}

val tripsModule = module {
    single { GetTripsUseCase(get(), get(), get()) }
    single { TripsAdapter() }
    viewModel { TripsViewModel(get()) }
}
