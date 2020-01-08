package com.unicorn.csp.ui.act

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.toIconicsColor
import com.mikepenz.iconics.utils.toIconicsSizeDp
import com.unicorn.csp.R
import com.unicorn.csp.ui.adapter.MainPagerAdapter
import com.unicorn.csp.ui.base.BaseAct
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
                    MainPagerAdapter.titles[0].substring(0,2)
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_calendar_check1,
                    MainPagerAdapter.titles[1].substring(0,2)
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_book_open,
                    MainPagerAdapter.titles[2].substring(0,2)
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_book,
                    MainPagerAdapter.titles[3].substring(0,2)
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_cog,
                    MainPagerAdapter.titles[4].substring(0,2)
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_comments1,
                    MainPagerAdapter.titles[5].substring(0,2)
                )
            )
            .build()
        navigationController.setupWithViewPager(viewPager)
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