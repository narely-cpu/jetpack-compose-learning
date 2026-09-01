package com.narely.feedbackjourney.features.managementuser.di

import com.narely.feedbackjourney.features.managementuser.data.ManagementUserRepository
import com.narely.feedbackjourney.features.managementuser.data.ManagementUserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ManagementUserModule {

    @Binds
    fun bindsManagementUserRepository(managementUserRepositoryImpl: ManagementUserRepositoryImpl): ManagementUserRepository
}