package com.peranidze.remote.user

import com.peranidze.remote.user.model.UserModel
import com.peranidze.remote.user.request.LogInRequestBody
import com.peranidze.remote.user.request.SignUpRequestBody
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("users/login")
    fun logIn(@Body logInRequestBody: LogInRequestBody): Single<UserModel>

    @POST("users/register")
    fun signUp(@Body signUpRequestBody: SignUpRequestBody): Single<UserModel>

    @GET("")
    fun getUsers(): Single<List<UserModel>>
}
