package com.unicorn.csp.ui.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unicorn.csp.R
import com.unicorn.csp.app.ObjectId
import com.unicorn.csp.app.displayDateFormat
import com.unicorn.csp.app.safeClicks
import com.unicorn.csp.data.model.ArticleNoImage
import com.unicorn.csp.data.model.ArticleWithImage
import com.unicorn.csp.data.model.noImage
import com.unicorn.csp.data.model.withImage
import com.unicorn.csp.ui.act.ArticleAct
import com.unicorn.csp.ui.base.KVHolder
import org.joda.time.DateTime
import java.util.ArrayList

class MultiArticleAdapter: BaseMultiItemQuickAdapter<MultiItemEntity,KVHolder>(ArrayList()){

    init {
        addItemType(withImage, R.layout.item_article_with_image)
        addItemType(noImage, R.layout.item_article_no_image)
    }

    override fun convert(helper: KVHolder, item: MultiItemEntity?) {
        when(item!!.itemType){
            withImage ->  {
                item as ArticleWithImage
                val article = item.article
                helper.setText(R.id.tvTitle,article.title)
                val ivImage = helper.getView<ImageView>(R.id.ivImage)
                Glide.with(mContext).load(article.cover).into(ivImage)
                helper.setText(R.id.tvPublishTime,DateTime(article.publishTime).toString(displayDateFormat))
                helper.getView<View>(R.id.root).safeClicks().subscribe {
                    Intent(mContext, ArticleAct::class.java).apply {
                        putExtra(ObjectId, article.objectId)
                    }.let { mContext.startActivity(it) }
                }
            }
            noImage -> {
                item as ArticleNoImage
                val article = item.article
                helper.setText(R.id.tvTitle,article.title)
                helper.setText(R.id.tvPublishTime,DateTime(article.publishTime).toString(displayDateFormat))
                helper.getView<View>(R.id.root).safeClicks().subscribe {
                    Intent(mContext, ArticleAct::class.java).apply {
                        putExtra(ObjectId, article.objectId)
                    }.let { mContext.startActivity(it) }
                }
            }
        }
    }

}