package com.unircorn.csp.app.di

import com.unircorn.csp.app.di.component.AppComponent
import com.unircorn.csp.app.di.component.DaggerAppComponent

object ComponentHolder {

    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }

}