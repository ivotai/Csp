package com.unicorn.csp.ui.act

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.blankj.utilcode.util.ConvertUtils
import com.unicorn.csp.R
import com.unicorn.csp.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_article_search.*

class ArticleSearchAct : BaseAct() {

    override val layoutId = R.layout.act_article_search

    override fun initViews() {
        super.initViews()
        etSearch.background = GradientDrawable().apply {
            setColor(Color.WHITE)
            cornerRadius = ConvertUtils.dp2px(10f).toFloat()
        }
    }

}