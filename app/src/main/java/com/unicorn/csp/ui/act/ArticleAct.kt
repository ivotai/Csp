package com.unicorn.csp.ui.act

import android.view.View
import com.hjq.bar.OnTitleBarListener
import com.unicorn.csp.R
import com.unicorn.csp.app.*
import com.unicorn.csp.app.helper.DialogHelper
import com.unicorn.csp.app.helper.ExceptionHelper
import com.unicorn.csp.data.model.Article
import com.unicorn.csp.ui.base.BaseAct
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_article.*

class ArticleAct : BaseAct() {

    override val layoutId = R.layout.act_article
    override fun initViews() {
        titleBar
    }

    override fun bindIntent() {
        titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                finish()
            }

            override fun onRightClick(v: View?) {

            }

            override fun onTitleClick(v: View?) {
            }
        })
        getArticle()
    }

    private fun getArticle() {
        val mask = DialogHelper.showMask(this)
        api.getArticle(objectId = intent.getStringExtra(ObjectId)!!)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    display(it.data)
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private fun display(article: Article) = article.apply {
        titleBar.title = title
        webView.loadData(content, "text/html", "utf-8")
    }

}