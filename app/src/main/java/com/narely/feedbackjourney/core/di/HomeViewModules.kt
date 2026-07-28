package com.narely.feedbackjourney.core.di

import com.google.gson.GsonBuilder
import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import com.narely.feedbackjourney.core.services.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object HomeViewModules {
    @Provides
    fun providesUserRepository(apiService: ApiService): UsersRepository {
        return UsersRepositoryImpl(null, apiService)
    }

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}