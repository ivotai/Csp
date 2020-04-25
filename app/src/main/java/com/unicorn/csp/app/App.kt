package com.unicorn.csp.app

import androidx.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.unicorn.csp.app.di.Holder
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        Holder.init(this)
        Kotpref.init(this)
//        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
    }

}