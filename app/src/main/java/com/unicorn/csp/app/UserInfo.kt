package com.unicorn.csp.app

import com.chibatching.kotpref.KotprefModel

object UserInfo : KotprefModel() {
    var username by stringPref()
    var password by stringPref()
}