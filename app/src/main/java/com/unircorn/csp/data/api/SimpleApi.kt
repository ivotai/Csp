package com.unircorn.csp.data.api

import com.unircorn.csp.app.Globals
import com.unircorn.csp.data.model.LoginResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface SimpleApi {

    @GET("login/account")
    fun login(@Query("username") username: String, @Query("password") password: String): Single<Any>

    @GET("login/silence")
    fun loginSilently(@Query("token") token: String = Globals.token): Call<LoginResponse>

}