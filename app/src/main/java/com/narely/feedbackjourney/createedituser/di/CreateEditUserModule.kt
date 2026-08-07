package com.narely.feedbackjourney.createedituser.di

import com.narely.feedbackjourney.createedituser.data.CreateEditUserRepository
import com.narely.feedbackjourney.createedituser.data.CreateEditUserRepositoryImpl
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