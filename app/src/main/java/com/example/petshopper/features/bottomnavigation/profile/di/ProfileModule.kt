package com.example.petshopper.features.bottomnavigation.profile.di

import com.example.petshopper.features.bottomnavigation.profile.data.api.UpdateUser
import com.example.petshopper.features.bottomnavigation.profile.data.repositoryimpl.UpdateUserRepositoryImpl
import com.example.petshopper.features.bottomnavigation.profile.domain.repository.UpdateUserRepository
import com.example.petshopper.features.bottomnavigation.profile.domain.usecase.UpdateUserUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds
    @Singleton
    abstract fun bindUpdateUserRepository(
        updateUserRepositoryImpl: UpdateUserRepositoryImpl
    ): UpdateUserRepository

    companion object {
        @Provides
        @Singleton
        fun provideUpdateUserRepository(retrofit: Retrofit): UpdateUser {
            return retrofit.create(UpdateUser::class.java)
        }

    }
}