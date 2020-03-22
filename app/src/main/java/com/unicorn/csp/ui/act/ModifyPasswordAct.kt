package com.unicorn.csp.ui.act

import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.hjq.bar.OnTitleBarListener
import com.unicorn.csp.R
import com.unicorn.csp.app.isEmpty
import com.unicorn.csp.app.trimText
import com.unicorn.csp.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_modify_password.*

class ModifyPasswordAct : BaseAct() {

    override fun initViews() {

    }

    override fun bindIntent() {
        titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                finish()
            }

            override fun onRightClick(v: View?) {
               modifyPasswordX()
            }

            override fun onTitleClick(v: View?) {
            }
        })
    }

    private fun modifyPasswordX() {
        fun modifyPassword() {
//            val i = UserChangePwdReq(
//                userMobile = Global.userInfo.userMobile,
//                oldPwd = etOldPwd.pwd(),
//                newPwd = etNewPwd.pwd()
//            )
//            userApi.changePwd(i).observeOnMain(this).subscribeBy(
//                onSuccess = {
//                    if (it.failed) return@subscribeBy
//                    ToastUtils.showShort("密码已修改，请重新登录")
//                    RxBus.post(NeedLoginEvent())
//                },
//                onError = Config.defaultOnError
//            )
        }

        if (etOldPwd.isEmpty()) {
            ToastUtils.showShort("旧密码不能为空")
            return
        }
        if (etNewPwd.isEmpty()) {
            ToastUtils.showShort("新密码不能为空")
            return
        }
        if (etConfirmPwd.isEmpty()) {
            ToastUtils.showShort("确认密码不能为空")
            return
        }
        if (etConfirmPwd.trimText() != etNewPwd.trimText()){
            ToastUtils.showShort("密码不一致")
            return
        }
        modifyPassword()
    }

    override val layoutId = R.layout.act_modify_password

}