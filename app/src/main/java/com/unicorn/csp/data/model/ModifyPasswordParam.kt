package com.unicorn.csp.data.model

data class ModifyPasswordParam(
    val newPassword: String,
    val originPassword: String
)
