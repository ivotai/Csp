package com.unicorn.csp.ui.act

import com.kaopiz.kprogresshud.KProgressHUD
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_article_pdf.*
import rxhttp.wrapper.param.RxHttp
import java.io.File

class ArticlePdfAct : BaseArticleAct() {

    override fun doAfterArticlePrepared() {
        val first = article.attachments[0]
        if (first.exists) pdfView.fromFile(first.file).load()
        else download()
    }

    private fun download() {
        val progressMask = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        val destPath = "${cacheDir.path}/demo.pdf"
        val url = "http://csp.seafa.kjgk.xyz:8000/dist/demo.pdf"
        RxHttp.get(url)
            .asDownload(
                destPath,
                { progressMask.setProgress(it.progress) },
                AndroidSchedulers.mainThread()
            )
            .subscribeBy(
                onNext = {
                    progressMask.dismiss()
                    pdfView.fromFile(File(it)).load()
                },
                onError = {
                    progressMask.dismiss()
                }
            )
    }

    override val layoutId: Int = com.unicorn.csp.R.layout.act_article_pdf

}