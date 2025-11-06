package com.example.petshopper.features.register.di

import com.example.petshopper.features.register.data.api.CreateUserAPI
import com.example.petshopper.features.register.data.repository.CreateUserRepoImpl
import com.example.petshopper.features.register.data.repository.CreateUserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RegisterModule {

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        impl: CreateUserRepoImpl
    ): CreateUserRepository

    companion object {
        @Provides
        @Singleton
        fun provideRegisterApiService(retrofit: Retrofit): CreateUserAPI {
            return retrofit.create(CreateUserAPI::class.java)
        }
    }
}