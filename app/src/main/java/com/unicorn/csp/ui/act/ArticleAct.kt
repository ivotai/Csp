package com.unicorn.csp.ui.act

import com.unicorn.csp.R
import kotlinx.android.synthetic.main.act_article.*

class ArticleAct : BaseArticleAct() {

    override fun doAfterArticlePrepared() {
        webView.loadDataWithBaseURL(null, article.content, "text/html", "utf-8", null);
    }

    override val layoutId = R.layout.act_article

}