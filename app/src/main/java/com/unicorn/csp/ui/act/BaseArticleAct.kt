package com.unicorn.csp.ui.act

import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjq.bar.OnTitleBarListener
import com.kaopiz.kprogresshud.KProgressHUD
import com.unicorn.csp.app.*
import com.unicorn.csp.app.helper.DialogHelper
import com.unicorn.csp.app.helper.ExceptionHelper
import com.unicorn.csp.app.helper.FileUtils2
import com.unicorn.csp.data.model.Article
import com.unicorn.csp.data.model.Attachment
import com.unicorn.csp.ui.base.BaseAct
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_article.*
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.param.RxHttp
import java.io.File

abstract class BaseArticleAct : BaseAct() {

    abstract fun doAfterArticlePrepared()

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
        val objectId = intent.getStringExtra(Param)!!
        api.getArticle(objectId = objectId)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    article = it.data
                    display()
                    doAfterArticlePrepared()
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private fun display() = article.apply {
        titleBar.title = title
        if (attachments.isNotEmpty()) {
            titleBar.rightTitle = "附件"
            titleBar.setOnTitleBarListener(object : OnTitleBarListener {
                override fun onLeftClick(v: View?) {
                    finish()
                }

                override fun onRightClick(v: View?) {
                    showAttachmentDialog()
                }

                override fun onTitleClick(v: View?) {
                }
            })
        }
    }

    private fun showAttachmentDialog() {
        MaterialDialog(this).show {
            listItems(items = article.attachments.map { it.filename }) { _, index, _ ->
                downloadAttachment(article.attachments[index])
            }
        }
    }

    private fun downloadAttachment(attachment: Attachment) = with(attachment) {
        if (exists) {
            FileUtils2.openFile(this@BaseArticleAct, file = file)
            return@with
        }
        val progressMask = KProgressHUD.create(this@BaseArticleAct)
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        val fullUrl = baseUrl + url
        RxHttp.get(fullUrl)
            .addHeader(Cookie, "${SESSION}=${Globals.session}")
            .asDownload(
                path,
                { progressMask.setProgress(it.progress) },
                AndroidSchedulers.mainThread()
            )
            .subscribeBy(
                onNext = {
                    progressMask.dismiss()
                    FileUtils2.openFile(this@BaseArticleAct, file = File(it))
                },
                onError = {
                    progressMask.dismiss()
                    if (it is HttpStatusCodeException) {
                        if (it.statusCode == "401")
                            ToastUtils.showLong("登陆超时，请重新登陆")
                    }
                }
            )
    }

    lateinit var article: Article

}
