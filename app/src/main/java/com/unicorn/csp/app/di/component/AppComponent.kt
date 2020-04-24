package com.unicorn.csp.app.di.component

import android.content.Context
import com.unicorn.csp.app.di.module.ApiModule
import com.unicorn.csp.app.di.module.BasicModule
import com.unicorn.csp.app.di.module.NetworkModule
import com.unicorn.csp.data.api.SimpleApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BasicModule::class, NetworkModule::class, ApiModule::class])
interface AppComponent {

    fun api(): SimpleApi

    fun context(): Context

}