package com.narely.feedbackjourney.features.home.di

import com.narely.feedbackjourney.features.home.data.HomeRepository
import com.narely.feedbackjourney.features.home.data.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface HomeModule {

    @Binds
    fun bindsHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}