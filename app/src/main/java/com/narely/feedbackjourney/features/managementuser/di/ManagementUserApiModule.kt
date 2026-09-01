package com.narely.feedbackjourney.features.managementuser.di

import com.narely.feedbackjourney.features.managementuser.data.remote.ManagementUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ManagementUserApiModule {

    @Provides
    fun providesHomeApi(retrofit: Retrofit): ManagementUserApi {
        return retrofit.create(ManagementUserApi::class.java)
    }
}