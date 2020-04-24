package com.unicorn.csp.ui.act

import android.net.Uri
import com.blankj.utilcode.util.AppUtils
import com.kaopiz.kprogresshud.KProgressHUD
import com.unicorn.csp.app.Param
import com.unicorn.csp.data.model.Article
import com.unicorn.csp.ui.base.BaseAct
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_article_pdf.*
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.param.RxHttp
import java.io.File

class ArticlePdfAct : BaseAct() {

    override fun initViews() {
        titleBar.title = article.title
    }

    override fun bindIntent() {
        download()
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

    private val article by lazy { intent.getSerializableExtra(Param) as Article }

    override val layoutId: Int = com.unicorn.csp.R.layout.act_article_pdf

}