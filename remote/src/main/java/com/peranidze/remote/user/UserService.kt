package com.peranidze.remote.user

import com.peranidze.remote.user.model.UserModel
import com.peranidze.remote.user.request.LogInRequestBody
import com.peranidze.remote.user.request.SignUpRequestBody
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface UserService {

    @POST("users/login")
    fun logIn(@Body logInRequestBody: LogInRequestBody): Single<UserModel>

    @POST("users/register")
    fun signUp(@Body signUpRequestBody: SignUpRequestBody): Single<UserModel>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Long): Single<UserModel>

    @GET("users/")
    fun getUsers(): Single<List<UserModel>>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: Long, @Body userModel: UserModel): Single<UserModel>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Long): Completable
}
