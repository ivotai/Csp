package com.unicorn.csp.ui.act

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.unicorn.csp.R
import com.unicorn.csp.app.*
import com.unicorn.csp.app.helper.DialogHelper
import com.unicorn.csp.app.helper.ExceptionHelper
import com.unicorn.csp.data.model.*
import com.unicorn.csp.ui.adapter.ReplyAdapter
import com.unicorn.csp.ui.base.KVHolder
import com.unicorn.csp.ui.base.SimplePageAct
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_topic.*

class TopicAct : SimplePageAct<Reply, KVHolder>() {

    override fun bindIntent() {
        super.bindIntent()

        mbCreateReply.safeClicks().subscribe { createReplyX() }
    }

    private fun createReplyX() {
        if (etContent.isEmpty()) {
            ToastUtils.showShort("回复内容不能为空")
            return
        }
        createReply()
    }

    private fun createReply() {
        val mask = DialogHelper.showMask(this)
        api.createReply(
            topicId = topic.objectId,
            createReplyParam = CreateReplyParam(content = etContent.trimText())
        )
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    etContent.setText("")
                    loadFirstPage()
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    override val simpleAdapter: BaseQuickAdapter<Reply, KVHolder> = ReplyAdapter()

    override fun loadPage(pageNo: Int): Single<Response<Page<Reply>>> =
        api.getReply(topicId = topic.objectId, pageNo = pageNo)

    private val topic by lazy { intent.getSerializableExtra(Topic) as Topic }

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

    override val layoutId = R.layout.act_topic

}