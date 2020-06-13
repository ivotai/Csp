package com.unicorn.csp.data.api

import com.unicorn.csp.app.Globals
import com.unicorn.csp.app.defaultPageSize
import com.unicorn.csp.data.model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface SimpleApi {

    @GET("login/account")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Single<LoginResponse>

    @GET("login/silence")
    fun loginSilently(@Query("token") token: String = Globals.token): Call<LoginResponse>

    @GET("api/v1/app/article")
    fun getArticle(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = defaultPageSize,
        @Query("category") category: String = "",
        @Query("keyword") keyword: String = ""
    ): Single<Response<Page<Article>>>

    @GET("api/v1/app/article/{objectId}")
    fun getArticle(@Path("objectId") objectId: String): Single<Response<Article>>

    @POST("api/v1/app/topic")
    fun createTopic(@Body createTopicParam: CreateTopicParam): Single<Response<Any>>

    @GET("api/v1/app/topic")
    fun getTopic(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = defaultPageSize
    ): Single<Response<Page<Topic>>>

    @GET("api/v1/app/topic/{topicId}/reply")
    fun getReply(
        @Path("topicId") topicId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = defaultPageSize
    ): Single<Response<Page<Reply>>>

    @POST("api/v1/app/topic/{topicId}/reply")
    fun createReply(
        @Path("topicId") topicId: String,
        @Body createReplyParam: CreateReplyParam
    ): Single<Response<Any>>

    @GET(value = "public/checkUpdate")
    fun checkUpdate(
        @Query("version") version: String,
        @Query("id") id: String = "1001"
    ): Observable<CheckUpdateResponse>

    @Headers("accept:application/json")
    @GET(value = "logout")
    fun logout(): Single<Response<Any>>

    @PUT("api/v1/app/modifyPassword")
    fun modifyPassword(@Body modifyPasswordParam: ModifyPasswordParam): Single<Response<Any>>

}