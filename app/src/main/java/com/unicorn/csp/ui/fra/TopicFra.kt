package com.unicorn.csp.ui.fra

import android.graphics.Color
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.toIconicsColor
import com.mikepenz.iconics.utils.toIconicsSizeDp
import com.unicorn.csp.R
import com.unicorn.csp.app.safeClicks
import com.unicorn.csp.app.startAct
import com.unicorn.csp.data.event.RefreshATopicEvent
import com.unicorn.csp.ui.act.CreateTopicAct
import com.unicorn.csp.ui.base.BaseFra
import com.unicorn.ticket.bs.app.RxBus
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fra_topic.*

class TopicFra : BaseFra() {

    override val layoutId = R.layout.fra_topic

    override fun initViews() {
        IconicsDrawable(this.context!!)
            .icon(FontAwesome.Icon.faw_plus)
            .color(Color.WHITE.toIconicsColor())
            .size(24.toIconicsSizeDp())
            .let { fabCreateTopic.setImageDrawable(it) }
    }

    override fun bindIntent() {
        super.bindIntent()

        fabCreateTopic.safeClicks().subscribe { context!!.startAct(CreateTopicAct::class.java) }
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, RefreshATopicEvent::class.java, Consumer {

        })
    }

}