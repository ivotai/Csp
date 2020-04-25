package com.unicorn.csp.ui.act

import com.blankj.utilcode.util.ToastUtils
import com.kaopiz.kprogresshud.KProgressHUD
import com.unicorn.csp.app.Cookie
import com.unicorn.csp.app.Globals
import com.unicorn.csp.app.SESSION
import com.unicorn.csp.app.baseUrl
import com.unicorn.csp.data.model.Attachment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_article_pdf.*
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.param.RxHttp

class ArticlePdfAct : BaseArticleAct() {

    override fun doAfterArticlePrepared() {
        val pdf = article.pdf
        if (pdf.exists) pdfView.fromFile(pdf.file).load()
        else download(pdf)
    }

    private fun download(pdf: Attachment) {
        val progressMask = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        val url = baseUrl + pdf.url
        RxHttp.get(url)
            .addHeader(Cookie, "$SESSION=${Globals.session}")
            .asDownload(
                pdf.path,
                { progressMask.setProgress(it.progress) },
                AndroidSchedulers.mainThread()
            )
            .subscribeBy(
                onNext = {
                    progressMask.dismiss()
                    pdfView.fromFile(pdf.file).load()
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

    override val layoutId: Int = com.unicorn.csp.R.layout.act_article_pdf

}