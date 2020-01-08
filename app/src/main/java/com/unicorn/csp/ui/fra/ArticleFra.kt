package com.unicorn.csp.ui.fra

import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.csp.app.Category
import com.unicorn.csp.data.model.Article
import com.unicorn.csp.data.model.Page
import com.unicorn.csp.data.model.Response
import com.unicorn.csp.ui.adapter.ArticleAdapter
import com.unicorn.csp.ui.base.KVHolder
import com.unicorn.csp.ui.base.SimplePageFra
import io.reactivex.Single

class ArticleFra : SimplePageFra<Article, KVHolder>() {

    override val simpleAdapter: BaseQuickAdapter<Article, KVHolder> = ArticleAdapter()

    override fun loadPage(pageNo: Int): Single<Response<Page<Article>>> =
        api.getArticle(pageNo = pageNo, category = arguments!!.getString(Category)!!)

}