package com.narely.feedbackjourney.createedituser.di

import com.narely.feedbackjourney.createedituser.data.remote.CreateEditUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object CreateEditUserApiModule {

    @Provides
    fun providesCreateEditUserApi(retrofit: Retrofit): CreateEditUserApi {
        return retrofit.create(CreateEditUserApi::class.java)
    }
}