package com.unicorn.csp.data.model

import com.blankj.utilcode.util.FileUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unicorn.csp.app.baseUrl
import com.unicorn.csp.app.di.Holder
import java.io.File
import java.io.Serializable

data class Article(
    val attachments: List<Attachment>,
    val category: String,
    val content: String,
    val objectId: String,
    val publishTime: Long,
    val publisher: String,
    val title: String,
    val cover: String,
    val type: Int,          // 1=图文，2=图文+视频，3=pdf
    val pdf: Attachment,
    val video: Attachment
) : Serializable

data class Attachment(
    val attachmentId: String,
    val filename: String,
    val url: String
) : Serializable {
    private val extension get() = FileUtils.getFileExtension(filename)
    private val uniqueFilename get() = "$attachmentId.$extension"
    val path get() = "${Holder.appComponent.context().cacheDir}/$uniqueFilename"
    val file get() = File(path)
    val exists get() = file.exists()
    val fullUrl get() = baseUrl + url
}

const val withImage = 0
const val noImage = 1

class ArticleWithImage(val article: Article) : MultiItemEntity {
    override fun getItemType(): Int = withImage
}

class ArticleNoImage(val article: Article) : MultiItemEntity {
    override fun getItemType(): Int = noImage
}