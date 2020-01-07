package com.unicorn.csp.app.di.component

import com.unicorn.csp.app.di.module.ApiModule
import com.unicorn.csp.app.di.module.NetworkModule
import com.unicorn.csp.data.api.SimpleApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ApiModule::class])
interface AppComponent {

    fun api(): SimpleApi

}