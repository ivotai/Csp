package com.unircorn.csp.app.di.component

import com.unircorn.csp.app.di.module.ApiModule
import com.unircorn.csp.app.di.module.NetworkModule
import com.unircorn.csp.data.api.SimpleApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ApiModule::class])
interface AppComponent {

    fun api(): SimpleApi

}