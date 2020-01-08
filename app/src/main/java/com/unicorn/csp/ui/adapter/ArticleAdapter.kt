package com.unicorn.csp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.csp.R
import com.unicorn.csp.data.model.Article
import com.unicorn.csp.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_article.*

class ArticleAdapter: BaseQuickAdapter<Article, KVHolder>(R.layout.item_article) {

    override fun convert(helper: KVHolder, item: Article) {
        helper.apply {
            tvTitle.text = item.title
        }
    }

}