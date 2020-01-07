package com.unicorn.csp.data.api

import com.unicorn.csp.app.Globals
import com.unicorn.csp.data.model.LoginResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface SimpleApi {

    @GET("login/account")
    fun login(@Query("username") username: String, @Query("password") password: String): Single<LoginResponse>

    @GET("login/silence")
    fun loginSilently(@Query("token") token: String = Globals.token): Call<LoginResponse>

}