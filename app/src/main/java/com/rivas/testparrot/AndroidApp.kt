package com.rivas.testparrot

import android.app.Application
import android.content.Context
import com.rivas.testparrot.di.AppComponent
import com.rivas.testparrot.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AndroidApp: Application(), HasAndroidInjector {

    companion object {
        lateinit var daggerAppComponent: AppComponent
        lateinit var appContext: Context
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    override fun onCreate() {
        super.onCreate()

        appContext = this
        daggerAppComponent = DaggerAppComponent.builder()
            .application(this)
            .context(this)
            .build()
        daggerAppComponent.inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}