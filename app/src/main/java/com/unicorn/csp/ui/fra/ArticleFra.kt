package com.unicorn.csp.ui.fra

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unicorn.csp.app.Category
import com.unicorn.csp.app.Title
import com.unicorn.csp.data.model.*
import com.unicorn.csp.ui.adapter.MultiArticleAdapter
import com.unicorn.csp.ui.base.KVHolder
import com.unicorn.csp.ui.base.SimplePageFra2
import com.unicorn.csp.ui.header.ArticleHeader
import io.reactivex.Single

class ArticleFra : SimplePageFra2<KVHolder>() {

    override val simpleAdapter: BaseMultiItemQuickAdapter<MultiItemEntity, KVHolder> =
        MultiArticleAdapter()

    override fun loadPage(pageNo: Int): Single<Response<Page<MultiItemEntity>>> =
        api.getArticle(pageNo = pageNo, category = arguments!!.getString(Category)!!)
            .map {
                val page = Page(
                    content = it.data.content.map { article ->
                        if (article.cover == "") ArticleNoImage(article)
                        else ArticleWithImage(article)
                    },
                    totalElements = it.data.totalElements
                )
                val response = Response(
                    message = it.message,
                    success = it.success,
                    data = page
                )
                return@map  response
            }

    override fun initViews() {
        super.initViews()
        simpleAdapter.addHeaderView(ArticleHeader(context!!, arguments!!.getString(Title)!!))
    }

}