package com.unicorn.csp.ui.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.csp.R
import com.unicorn.csp.app.ObjectId
import com.unicorn.csp.app.safeClicks
import com.unicorn.csp.data.model.Article
import com.unicorn.csp.ui.act.ArticleAct
import com.unicorn.csp.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_article.*

class ArticleAdapter : MyAdapter<Article, KVHolder>(R.layout.item_article) {

    override fun convert(helper: KVHolder, item: Article) {
        helper.apply {
            tvTitle.text = item.title
        }
        helper.root.safeClicks().subscribe {
            Intent(mContext, ArticleAct::class.java).apply {
                putExtra(ObjectId, item.objectId)
            }.let { mContext.startActivity(it) }
        }
    }

    override fun bindIntent(helper: KVHolder, viewType: Int) {

    }

}