package com.unicorn.csp.ui.base

interface UI {

    val layoutId: Int

    fun inject()

    fun initViews()

    fun bindIntent()

    fun registerEvent()

}