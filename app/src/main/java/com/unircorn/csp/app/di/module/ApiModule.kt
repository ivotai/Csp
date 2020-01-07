package com.unircorn.csp.app.di.module

import com.unircorn.csp.data.api.SimpleApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun api(retrofit: Retrofit): SimpleApi = retrofit.create(SimpleApi::class.java)

}