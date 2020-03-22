package com.unicorn.csp.ui.act

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ActivityUtils
import com.hjq.bar.OnTitleBarListener
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.toIconicsColor
import com.mikepenz.iconics.utils.toIconicsSizeDp
import com.unicorn.csp.R
import com.unicorn.csp.app.Globals
import com.unicorn.csp.app.helper.ExceptionHelper
import com.unicorn.csp.app.observeOnMain
import com.unicorn.csp.app.startAct
import com.unicorn.csp.app.toActAndFinish
import com.unicorn.csp.data.event.LogoutEvent
import com.unicorn.csp.ui.adapter.MainPagerAdapter
import com.unicorn.csp.ui.base.BaseAct
import com.unicorn.ticket.bs.app.RxBus
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_main.*
import me.majiajie.pagerbottomtabstrip.item.NormalItemView

class MainAct : BaseAct() {

    override fun initViews() {
        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager)
        viewPager.adapter = mainPagerAdapter
        viewPager.offscreenPageLimit = MainPagerAdapter.titles.size - 1

        val navigationController = tab.custom()
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_balance_scale,
                    MainPagerAdapter.titles[0].substring(0, 2)
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_calendar_check1,
                    MainPagerAdapter.titles[1].substring(0, 2)
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_book_open,
                    MainPagerAdapter.titles[2].substring(0, 2)
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_book,
                    MainPagerAdapter.titles[3].substring(0, 2)
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_cog,
                    MainPagerAdapter.titles[4].substring(0, 2)
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_comments1,
                    MainPagerAdapter.titles[5].substring(0, 2)
                )
            )
            .build()
        navigationController.setupWithViewPager(viewPager)

        titleBar.title = MainPagerAdapter.titles[0]
        navigationController.addSimpleTabItemSelectedListener { index, _ ->
            titleBar.title = MainPagerAdapter.titles[index]
        }
    }

    override fun bindIntent() {
        titleBar.setRightTitle("我的").setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
            }

            override fun onRightClick(v: View?) {
                startAct(MyAct::class.java)
            }

            override fun onTitleClick(v: View?) {
            }
        })
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, LogoutEvent::class.java, Consumer {
            ActivityUtils.finishAllActivities()
            startAct(LoginAct::class.java)
        })
    }

    private fun newItem(icon: IIcon, text: String) =
        NormalItemView(this).apply {
            val colorPrimary = ContextCompat.getColor(this@MainAct, R.color.colorPrimary)
            initialize(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, text)
            setDefaultDrawable(
                IconicsDrawable(this@MainAct)
                    .icon(icon)
                    .color(Color.GRAY.toIconicsColor())
                    .size(24.toIconicsSizeDp())
            )
            setSelectedDrawable(
                IconicsDrawable(this@MainAct)
                    .icon(icon)
                    .color(colorPrimary.toIconicsColor())
                    .size(24.toIconicsSizeDp())
            )
            setTextDefaultColor(Color.GRAY)
            setTextCheckedColor(colorPrimary)
        }

    override val layoutId = R.layout.act_main

}