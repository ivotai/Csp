package com.unicorn.csp.ui.act

import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.hjq.bar.OnTitleBarListener
import com.unicorn.csp.R
import com.unicorn.csp.app.*
import com.unicorn.csp.app.helper.DialogHelper
import com.unicorn.csp.app.helper.ExceptionHelper
import com.unicorn.csp.data.event.RefreshTopicEvent
import com.unicorn.csp.data.model.CreateTopicParam
import com.unicorn.csp.data.model.Topic
import com.unicorn.csp.ui.base.BaseAct
import com.unicorn.ticket.bs.app.RxBus
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_create_topic.*

class CreateTopicAct : BaseAct() {

    override val layoutId = R.layout.act_create_topic

    override fun initViews() {
    }

    override fun bindIntent() {
        titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                finish()
            }

            override fun onRightClick(v: View?) {
                createTopicX()
            }

            override fun onTitleClick(v: View?) {
            }
        })
    }

    private fun createTopicX() {
        if (etTitle.isEmpty()) {
            ToastUtils.showShort("标题不能为空")
            return
        }
        if (etContent.isEmpty()) {
            ToastUtils.showShort("内容不能为空")
            return
        }
        createTopic()
    }

    private fun createTopic() {
        val mask = DialogHelper.showMask(this)
        api.createTopic(
            CreateTopicParam(
                title = etTitle.trimText(),
                content = etContent.trimText()
            )
        )
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    ToastUtils.showShort("发帖成功")
                    RxBus.post(RefreshTopicEvent())
                    finish()
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

}