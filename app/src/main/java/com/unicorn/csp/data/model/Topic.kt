package com.unicorn.csp.data.model

import java.io.Serializable

data class Topic(
    val issueTime: Long,
    val issuer: String ,
    val objectId: String ,
    val replyCount: Int ,
    val content: String,
    val title: String
) : Serializable

data class CreateTopicParam(
    val content: String,
    val title:String
)