package com.unicorn.csp.ui.header

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.unicorn.csp.R
import com.unicorn.csp.data.model.Topic
import kotlinx.android.synthetic.main.header_topic.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class TopicHeader(context: Context, private val topic: Topic) : FrameLayout(context) {

    private val prettyTime = PrettyTime()

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.header_topic, this, true)
        with(topic){
            tvIssuer.text = issuer
            tvIssueTime.text =prettyTime.format(Date(issueTime))
            tvContent.text = content
        }
    }

}