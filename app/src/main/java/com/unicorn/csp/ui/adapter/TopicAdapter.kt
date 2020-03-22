package com.unicorn.csp.ui.adapter

import android.content.Intent
import com.unicorn.csp.R
import com.unicorn.csp.app.Topic
import com.unicorn.csp.app.displayDateFormat
import com.unicorn.csp.app.safeClicks
import com.unicorn.csp.data.model.Topic
import com.unicorn.csp.ui.act.TopicAct
import com.unicorn.csp.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_topic.*
import org.joda.time.DateTime

class TopicAdapter : BaseAdapter<Topic, KVHolder>(R.layout.item_topic) {

    override fun convert(helper: KVHolder, item: Topic) {
        helper.apply {
            tvTitle.text = item.title
            tvReplyCount.text = item.replyCount.toString()
            tvIssueTime.text = DateTime(item.issueTime).toString(displayDateFormat)
        }
        helper.root.safeClicks().subscribe {
            Intent(mContext, TopicAct::class.java).apply {
                putExtra(Topic, item)
            }.let { mContext.startActivity(it) }
        }
    }

    override fun bindIntent(helper: KVHolder, viewType: Int) {

    }

}