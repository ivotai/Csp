package com.unicorn.csp.app.helper

import com.unicorn.csp.app.Cookie
import com.unicorn.csp.app.Globals
import com.unicorn.csp.app.SESSION
import com.unicorn.csp.app.di.Holder
import okhttp3.Interceptor
import okhttp3.Response

object NetworkHelper {

    fun proceedRequestWithNewSession(chain: Interceptor.Chain): Response {
        // session 过期时使用 token 登录，获取新的 session 和 token。
        api.loginSilently().execute().body().let { Globals.loginResponse = it!! }
        return proceedRequestWithSession(chain)
    }

    fun proceedRequestWithSession(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .removeHeader(Cookie)
            .addHeader(Cookie, "${SESSION}=${Globals.session}")
            .build()
            .let { chain.proceed(it) }
    }

    private val api by lazy { Holder.appComponent.api() }

}