package com.unicorn.csp.app

import com.unicorn.csp.data.model.LoginResponse
import com.unicorn.csp.data.model.User

object Globals {

    val session: String get() = loginResponse.session
    val token: String get() = loginResponse.loginToken
    val user: User get() = loginResponse.user

    lateinit var loginResponse: LoginResponse

}