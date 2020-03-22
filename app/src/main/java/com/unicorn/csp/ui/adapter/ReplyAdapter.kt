package com.unicorn.csp.ui.adapter

import com.unicorn.csp.R
import com.unicorn.csp.data.model.Reply
import com.unicorn.csp.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_reply.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class ReplyAdapter : BaseAdapter<Reply, KVHolder>(R.layout.item_reply) {

    private val prettyTime =  PrettyTime()

    override fun convert(helper: KVHolder, item: Reply) {
        helper.apply {
            tvIssuer.text = item.issuer
            tvIssueTime.text =prettyTime.format(Date(item.issueTime))
            tvContent.text = item.content
        }
    }

    override fun bindIntent(helper: KVHolder, viewType: Int) {

    }

}