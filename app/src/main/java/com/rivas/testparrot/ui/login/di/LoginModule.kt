package com.rivas.testparrot.ui.login.di

import com.rivas.testparrot.repository.Repository
import com.rivas.testparrot.ui.login.LoginViewModel
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    fun provideViewModel(repository: Repository) = LoginViewModel(repository)
}