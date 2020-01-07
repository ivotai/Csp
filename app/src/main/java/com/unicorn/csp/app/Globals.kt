package com.unicorn.csp.app

import com.unicorn.csp.data.model.LoginResponse

object Globals {

    val session: String get() = loginResponse.session
    val token: String get() = loginResponse.loginToken

    lateinit var loginResponse: LoginResponse

}