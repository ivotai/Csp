package com.unicorn.csp.ui.act

import com.blankj.utilcode.util.ToastUtils
import com.unicorn.csp.R
import com.unicorn.csp.app.*
import com.unicorn.csp.app.helper.DialogHelper
import com.unicorn.csp.app.helper.ExceptionHelper
import com.unicorn.csp.ui.base.BaseAct
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_login.*

class LoginAct : BaseAct() {

    override val layoutId = R.layout.act_login

    override fun initViews() {
        with(UserInfo) {
            etUsername.setText(username)
            etPassword.setText(password)
        }
    }

    override fun bindIntent() {
        mbLogin.safeClicks().subscribe { loginX() }
    }

    private fun loginX() {
        if (etUsername.isEmpty()) {
            ToastUtils.showShort("用户名不能为空")
            return
        }
        if (etPassword.isEmpty()) {
            ToastUtils.showShort("密码不能为空")
            return
        }
        login()
    }

    private fun login() {
        val mask = DialogHelper.showMask(this)
        api.login(etUsername.trimText(), etPassword.trimText())
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    Globals.loginResponse = it
                    with(UserInfo) {
                        username = etUsername.trimText()
                        password = etPassword.trimText()
                    }
                    toActAndFinish(MainAct::class.java)
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

}