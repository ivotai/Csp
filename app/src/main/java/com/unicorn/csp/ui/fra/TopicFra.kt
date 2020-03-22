package com.unicorn.csp.ui.fra

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.csp.R
import com.unicorn.csp.app.safeClicks
import com.unicorn.csp.app.startAct
import com.unicorn.csp.data.event.RefreshEvent
import com.unicorn.csp.data.model.Page
import com.unicorn.csp.data.model.Response
import com.unicorn.csp.data.model.Topic
import com.unicorn.csp.ui.act.CreateTopicAct
import com.unicorn.csp.ui.adapter.TopicAdapter
import com.unicorn.csp.ui.base.KVHolder
import com.unicorn.csp.ui.base.SimplePageFra
import com.unicorn.ticket.bs.app.RxBus
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fra_topic.*

class TopicFra : SimplePageFra<Topic, KVHolder>() {

    override val simpleAdapter: BaseQuickAdapter<Topic, KVHolder> = TopicAdapter()

    override fun loadPage(pageNo: Int): Single<Response<Page<Topic>>> =
        api.getTopic(pageNo = pageNo)

    override fun initViews() {
        super.initViews()
    }

    override fun bindIntent() {
        super.bindIntent()
        fabCreateTopic.safeClicks().subscribe { context!!.startAct(CreateTopicAct::class.java) }
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, RefreshEvent::class.java, Consumer {
            loadFirstPage()
        })
    }

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

    override val layoutId = R.layout.fra_topic

}