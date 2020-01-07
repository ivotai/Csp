package com.unircorn.csp.app

import com.unircorn.csp.data.model.LoginResponse

object Globals {

    val session: String get() = loginResponse.session
    val token: String get() = loginResponse.loginToken

    lateinit var loginResponse: LoginResponse

}