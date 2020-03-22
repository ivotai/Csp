package com.unicorn.csp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.csp.R
import com.unicorn.csp.app.safeClicks
import com.unicorn.csp.data.event.LogoutEvent
import com.unicorn.csp.data.model.MyMenu
import com.unicorn.csp.ui.base.KVHolder
import com.unicorn.ticket.bs.app.RxBus
import kotlinx.android.synthetic.main.item_my.*

class MyAdapter : BaseQuickAdapter<MyMenu, KVHolder>(R.layout.item_my) {

    override fun convert(helper: KVHolder, item: MyMenu) {
        helper.apply {
            tvText.text = item.text
            root.safeClicks().subscribe {
                when (item) {
                    MyMenu.Logout -> RxBus.post(LogoutEvent())
                    else -> { }
                }
            }
        }
    }

}