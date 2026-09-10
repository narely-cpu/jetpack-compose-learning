package com.narely.feedbackjourney.features.managementuser.di

import com.narely.feedbackjourney.core.singleton.AuthSingleton
import com.narely.feedbackjourney.features.managementuser.data.ManagementUserRepository
import com.narely.feedbackjourney.features.managementuser.data.ManagementUserRepositoryImpl
import com.narely.feedbackjourney.features.managementuser.data.remote.ManagementUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ManagementUserModule {

    @Provides
    fun providesManagementUserRepository(managementUserApi: ManagementUserApi): ManagementUserRepository {
        return ManagementUserRepositoryImpl(
            managementUserApi = managementUserApi,
            token = AuthSingleton.getToken().orEmpty()
        )
    }
}