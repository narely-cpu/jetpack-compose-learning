package com.narely.feedbackjourney.features.createedituser.di

import com.narely.feedbackjourney.features.createedituser.data.CreateEditUserRepository
import com.narely.feedbackjourney.features.createedituser.data.CreateEditUserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface CreateEditUserModule {

    @Binds
    fun bindsCreateEditUserRepository(createEditUserRepositoryImpl: CreateEditUserRepositoryImpl): CreateEditUserRepository
}