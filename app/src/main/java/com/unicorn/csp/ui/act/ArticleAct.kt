package com.unicorn.csp.ui.act

import android.content.Intent
import android.os.Environment
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjq.bar.OnTitleBarListener
import com.unicorn.csp.R
import com.unicorn.csp.app.*
import com.unicorn.csp.app.helper.DialogHelper
import com.unicorn.csp.app.helper.ExceptionHelper
import com.unicorn.csp.app.helper.FileUtils2
import com.unicorn.csp.app.helper.NetworkHelper
import com.unicorn.csp.data.model.Article
import com.unicorn.csp.data.model.Attachment
import com.unicorn.csp.ui.base.BaseAct
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.FileCallBack
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_article.*
import okhttp3.Call
import java.io.File
import java.io.IOException

class ArticleAct : BaseAct() {

    override val layoutId = R.layout.act_article
    override fun initViews() {
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
                    article = it.data
                    display()
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private fun display() = article.apply {
        if (this.attachments.isNotEmpty()) {
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
        titleBar.title = title
        webView.loadData(content, "text/html", "utf-8")
    }

    private fun showAttachmentDialog() {
        MaterialDialog(this).show {
            listItems(items = article.attachments.map { it.filename }) { dialog, index, text ->
                download(article.attachments[index])
            }
        }
    }

    fun baseDir(): String {
        val downloadDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val baseDir = File(downloadDir, "Csp")
        if (!baseDir.exists()) baseDir.mkdir()
        return baseDir.absolutePath
    }

    private fun download(attachment: Attachment) {
        val mask = DialogHelper.showMask(this)
        val fullUrl = baseUrl + attachment.url
        OkHttpUtils
            .get()
            .url(fullUrl)
            .addHeader(Cookie, "${SESSION}=${Globals.session}")
            .build()
            .execute(object : FileCallBack(baseDir(), attachment.filename) {
                override fun onResponse(response: File, id: Int) {
                    mask.dismiss()

                    startPdf()
//                    FileUtils2.openFile(this@ArticleAct, file = response)
                }

                override fun onError(call: Call?, e: Exception?, id: Int) {
                    mask.dismiss()
                    if (e?.message == "request failed , reponse's code is : 401") {
                        ToastUtils.showShort("登录超时，请重新登录以查看附件")
                    }
                }
            })
    }

    lateinit var article: Article

    private fun startPdf(){
        Intent(this,ArticlePdfAct::class.java).apply {
            putExtra(Param,article)
        }.let { startActivity(it) }
    }
}