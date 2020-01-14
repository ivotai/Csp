package com.unicorn.csp.ui.adapter

import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.csp.R
import com.unicorn.csp.app.ObjectId
import com.unicorn.csp.app.displayDateFormat
import com.unicorn.csp.app.safeClicks
import com.unicorn.csp.data.model.Article
import com.unicorn.csp.ui.act.ArticleAct
import com.unicorn.csp.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_article.*
import org.joda.time.DateTime

class ArticleAdapter : MyAdapter<Article, KVHolder>(R.layout.item_article) {

    override fun convert(helper: KVHolder, item: Article) {
        helper.apply {
            tvTitle.text = item.title
            if(item.cover == ""){
                ivImage.visibility = View.GONE
            }else{
                ivImage.visibility = View.VISIBLE
                Glide.with(mContext).load(item.cover).into(ivImage)
            }
            tvPublishTime.text = DateTime(item.publishTime).toString(displayDateFormat)
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