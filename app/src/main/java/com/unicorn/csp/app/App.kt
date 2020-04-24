package com.unicorn.csp.app

import androidx.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref
import com.unicorn.csp.app.di.Holder

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        Holder.init(this)
        Kotpref.init(this)
    }

}