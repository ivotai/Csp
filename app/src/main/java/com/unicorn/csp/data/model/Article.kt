package com.unicorn.csp.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity

data class Article(
    val attachments: List<Attachment>,
    val category: String,
    val content: String,
    val objectId: String,
    val publishTime: Long,
    val publisher: String,
    val title: String,
    val cover:String
)

data class Attachment(
    val attachmentId: String,
    val filename: String,
    val url: String
)

const val withImage = 0
const val noImage = 1

class ArticleWithImage(val article: Article) : MultiItemEntity {
    override fun getItemType(): Int =withImage
}

class ArticleNoImage(val article: Article) : MultiItemEntity {
    override fun getItemType(): Int = noImage
}