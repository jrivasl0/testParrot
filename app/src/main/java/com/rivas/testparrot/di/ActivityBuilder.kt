package com.rivas.testparrot.di

import com.rivas.testparrot.ui.login.LoginActivity
import com.rivas.testparrot.ui.login.di.LoginModule
import com.rivas.testparrot.ui.menu.MenuActivity
import com.rivas.testparrot.ui.menu.di.MenuModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLogin(): LoginActivity

    @ContributesAndroidInjector(modules = [MenuModule::class])
    abstract fun bindMenu(): MenuActivity
}