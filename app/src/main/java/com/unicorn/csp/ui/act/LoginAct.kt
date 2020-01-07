package com.unicorn.csp.ui.act

import com.unicorn.csp.R
import com.unicorn.csp.app.Globals
import com.unicorn.csp.app.helper.DialogHelper
import com.unicorn.csp.app.helper.ExceptionHelper
import com.unicorn.csp.app.observeOnMain
import com.unicorn.csp.app.safeClicks
import com.unicorn.csp.app.trimText
import com.unicorn.csp.ui.base.BaseAct
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_login.*

class LoginAct : BaseAct() {

    override val layoutId = R.layout.act_login

    override fun initViews() {
        etUsername.setText("13611840424")
        etPassword.setText("111111")
    }

    override fun bindIntent() {
        btnLogin.safeClicks().subscribe {
            login()
        }
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
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

}