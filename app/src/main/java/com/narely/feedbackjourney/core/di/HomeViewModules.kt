package com.narely.feedbackjourney.core.di

import com.narely.feedbackjourney.core.data.UsersRepository
import com.narely.feedbackjourney.core.data.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HomeViewModules {
    @Provides
    fun providesUserRepository(): UsersRepository {
        return UsersRepositoryImpl(null)
    }

}