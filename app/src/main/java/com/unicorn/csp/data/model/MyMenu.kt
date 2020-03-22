package com.unicorn.csp.data.model

enum class MyMenu(val text: String) {

    ModifyPassword("修改密码"),
    Logout("退出登录")
    ;

    companion object {
        val all get() = listOf(ModifyPassword, Logout)
    }

}

