package com.unicorn.csp.ui.adapter

import com.unicorn.csp.R
import com.unicorn.csp.data.model.Reply
import com.unicorn.csp.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_reply.*

class ReplyAdapter : MyAdapter<Reply, KVHolder>(R.layout.item_reply) {

    override fun convert(helper: KVHolder, item: Reply) {
        helper.apply {
            tvIssuer.text = item.issuer
            tvIssueTime.text = item.issueTime.toString()
            tvContent.text = item.content
        }
    }

    override fun bindIntent(helper: KVHolder, viewType: Int) {

    }

}