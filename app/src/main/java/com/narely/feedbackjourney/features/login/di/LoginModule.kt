package com.narely.feedbackjourney.features.login.di

import com.narely.feedbackjourney.features.login.data.LoginRepository
import com.narely.feedbackjourney.features.login.data.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface LoginModule {

    @Binds
    fun bindsLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}