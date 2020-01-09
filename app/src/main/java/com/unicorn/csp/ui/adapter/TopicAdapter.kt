package com.unicorn.csp.ui.adapter

import com.unicorn.csp.R
import com.unicorn.csp.data.model.Topic
import com.unicorn.csp.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_article.*

class TopicAdapter : MyAdapter<Topic, KVHolder>(R.layout.item_topic) {

    override fun convert(helper: KVHolder, item: Topic) {
        helper.apply {
            tvTitle.text = item.title
        }
    }

    override fun bindIntent(helper: KVHolder, viewType: Int) {

    }

}