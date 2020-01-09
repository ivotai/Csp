package com.unicorn.csp.data.model

data class Topic(
    val issueTime: Long,
    val issuer: String,
    val objectId: String,
    val replyCount: Int,
    val content: String,
    val title: String
)
