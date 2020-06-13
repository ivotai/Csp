package com.unicorn.csp.ui.act

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.jakewharton.rxbinding3.widget.textChanges
import com.unicorn.csp.R
import com.unicorn.csp.app.Category
import com.unicorn.csp.app.trimText
import com.unicorn.csp.data.model.ArticleNoImage
import com.unicorn.csp.data.model.ArticleWithImage
import com.unicorn.csp.data.model.Page
import com.unicorn.csp.data.model.Response
import com.unicorn.csp.ui.adapter.MultiArticleAdapter
import com.unicorn.csp.ui.base.BaseAct
import com.unicorn.csp.ui.base.KVHolder
import com.unicorn.csp.ui.base.SimplePageAct
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.act_article_search.*
import java.util.concurrent.TimeUnit

class ArticleSearchAct : SimplePageAct<MultiItemEntity, KVHolder>() {

    override val simpleAdapter: BaseQuickAdapter<MultiItemEntity, KVHolder> = MultiArticleAdapter()

    override fun loadPage(pageNo: Int): Single<Response<Page<MultiItemEntity>>> =
        api.getArticle(page = pageNo, keyword = etSearch.trimText())
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
                return@map response
            }

    override fun initViews() {
        super.initViews()
        etSearch.background = GradientDrawable().apply {
            setColor(Color.WHITE)
            cornerRadius = ConvertUtils.dp2px(10f).toFloat()
        }
    }

    override fun bindIntent() {
        super.bindIntent()
        etSearch.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { loadFirstPage() }
    }

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

    override val layoutId = R.layout.act_article_search

}