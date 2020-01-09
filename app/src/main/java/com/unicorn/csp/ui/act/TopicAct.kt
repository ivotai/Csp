package com.unicorn.csp.ui.act

import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.csp.R
import com.unicorn.csp.app.Topic
import com.unicorn.csp.data.model.Page
import com.unicorn.csp.data.model.Reply
import com.unicorn.csp.data.model.Response
import com.unicorn.csp.data.model.Topic
import com.unicorn.csp.ui.adapter.ReplyAdapter
import com.unicorn.csp.ui.base.KVHolder
import com.unicorn.csp.ui.base.SimplePageAct
import io.reactivex.Single

class TopicAct : SimplePageAct<Reply, KVHolder>() {

    override val simpleAdapter: BaseQuickAdapter<Reply, KVHolder> = ReplyAdapter()

    override fun loadPage(pageNo: Int): Single<Response<Page<Reply>>> =
        api.getReply(topicId = topic.objectId, pageNo = pageNo)

    override val layoutId = R.layout.act_topic

    private val topic by lazy { intent.getSerializableExtra(Topic) as Topic }

}