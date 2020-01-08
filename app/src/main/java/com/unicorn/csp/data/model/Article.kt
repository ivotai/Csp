package com.unicorn.csp.data.model

data class Article(
    val attachments: List<Attachment>,
    val category: String,
    val content: String,
    val objectId: String,
    val publishTime: Long,
    val publisher: String,
    val title: String
)

data class Attachment(
    val attachmentId: String,
    val filename: String,
    val url: String
)