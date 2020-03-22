package com.unicorn.csp.data.model

import com.blankj.utilcode.util.AppUtils

enum class MyMenu(val text: String) {

    ModifyPassword("修改密码"),
    Logout("退出登录"),
    VersionName("版本号：${AppUtils.getAppVersionName()}")
    ;

    companion object {
        val all get() = listOf(ModifyPassword, Logout, VersionName)
    }

}

