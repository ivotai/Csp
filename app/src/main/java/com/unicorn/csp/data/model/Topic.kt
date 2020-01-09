package com.unicorn.csp.data.model

import java.io.Serializable

data class Topic(
    val issueTime: Long = 0,
    val issuer: String = "",
    val objectId: String = "",
    val replyCount: Int = 0,
    val content: String,
    val title: String
) : Serializable
