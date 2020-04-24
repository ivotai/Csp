package com.unicorn.csp.app.helper

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AppUtils
import com.kaopiz.kprogresshud.KProgressHUD
import com.unicorn.csp.app.di.Holder
import com.unicorn.csp.app.observeOnMain
import com.unicorn.csp.app.toActAndFinish
import com.unicorn.csp.ui.act.MainAct
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.FileCallBack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.Call
import rxhttp.wrapper.param.RxHttp
import java.io.File

object UpdateHelper {

    fun checkVersion(activity: AppCompatActivity) {
        val api = Holder.appComponent.api()
        api.checkUpdate(version = AppUtils.getAppVersionName())
            .observeOnMain(activity)
            .subscribeBy(
                onNext = {
                    if (it.newVersion)
                        download(activity = activity, apkUrl = it.apkUrl)
                    else
                        activity.toActAndFinish(MainAct::class.java)
                },
                onError = {
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private fun download(activity: AppCompatActivity, apkUrl: String) {
        val progressMask = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        RxHttp.get(apkUrl)
            .asDownload(
                "${Holder.appComponent.context().filesDir.path}/vehicle.apk",
                { progressMask.setProgress(it.progress) },
                AndroidSchedulers.mainThread()
            )
            .subscribeBy(
                onNext = {
                    progressMask.dismiss()
                    AppUtils.installApp(File(it))
                },
                onError = {
                    progressMask.dismiss()
                }
            )
    }

}