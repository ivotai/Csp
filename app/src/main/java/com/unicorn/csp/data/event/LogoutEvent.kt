package com.unicorn.csp.data.event

import java.io.Serializable

data class LogoutEvent(
    val clearPassword: Boolean = false
) : Serializable