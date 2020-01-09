package com.unicorn.csp.data.model

data class Reply(
    val content: String,
    val issueTime: Long,
    val issuer: String,
    val objectId: String
)

data class CreateReplyParam(val content:String)