package com.peranidze.remote.user

import com.peranidze.remote.user.model.UserModel
import com.peranidze.remote.user.request.CreateUserRequestBody
import com.peranidze.remote.user.request.LogInRequestBody
import com.peranidze.remote.user.request.SignUpRequestBody
import com.peranidze.remote.user.request.UpdateUserRequestBody
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface UserService {

    @POST("authenticate")
    fun logIn(@Body logInRequestBody: LogInRequestBody): Single<UserModel>

    @POST("register")
    fun register(@Body signUpRequestBody: SignUpRequestBody): Single<UserModel>

    @GET("users/{login}")
    fun getUser(@Path("login") login: String): Single<UserModel>

    @GET("usersByRole/")
    fun getUsers(): Single<List<UserModel>>

    @PUT("users/")
    fun updateUser(@Body updateUserRequestBody: UpdateUserRequestBody): Single<UserModel>

    @POST("users/")
    fun createUser(@Body createUserRequestBody: CreateUserRequestBody): Single<UserModel>

    @DELETE("users/{login}")
    fun deleteUser(@Path("login") login: String): Completable
}
