package com.unicorn.csp.ui.act

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.hjq.bar.OnTitleBarListener
import com.unicorn.csp.R
import com.unicorn.csp.app.Globals
import com.unicorn.csp.app.helper.ExceptionHelper
import com.unicorn.csp.app.observeOnMain
import com.unicorn.csp.data.model.MyMenu
import com.unicorn.csp.ui.adapter.MyAdapter
import com.unicorn.csp.ui.base.BaseAct
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_my.*

class MyAct : BaseAct() {

    override fun initViews() {
        constraintLayout1.background = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(colorPrimary, blue300)
        )

        tvUsername.text = Globals.user.username
        rtvCourtName.text = Globals.user.courtName

        fun initRecyclerView() {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                simpleAdapter.bindToRecyclerView(this)
                HorizontalDividerItemDecoration.Builder(context)
                    .colorResId(R.color.md_grey_300)
                    .margin(ConvertUtils.dp2px(16f), 0)
                    .size(1)
                    .build().let { this.addItemDecoration(it) }
            }
        }
        initRecyclerView()
    }

    override fun bindIntent() {
        simpleAdapter.setNewData(MyMenu.all)

        titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                finish()
            }

            override fun onRightClick(v: View?) {
            }

            override fun onTitleClick(v: View?) {
            }
        })

        api.getSummary().observeOnMain(this).subscribeBy(
            onSuccess = {
                tvReadCount.text = it.data.readCount
                tvTopicCount.text = it.data.topicCount
                tvReplyCount.text = it.data.replyCount
            },
            onError = {
                ExceptionHelper.showPrompt(it)
            }
        )
    }

    private val simpleAdapter = MyAdapter()

    private val colorPrimary by lazy { ContextCompat.getColor(this, R.color.colorPrimary) }
    private val blue300 by lazy { ContextCompat.getColor(this, R.color.md_red_300) }

    override val layoutId = R.layout.act_my

}