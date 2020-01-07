package com.unicorn.csp.app.di

import com.unicorn.csp.app.di.component.AppComponent
import com.unicorn.csp.app.di.component.DaggerAppComponent

object ComponentHolder {

    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }

}