package com.unicorn.csp.ui.header

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.unicorn.csp.R
import kotlinx.android.synthetic.main.header_article.view.*

class ArticleHeader(context: Context, private val title:String) : FrameLayout(context) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.header_article, this, true)
        tvTitle.text = title
    }

}