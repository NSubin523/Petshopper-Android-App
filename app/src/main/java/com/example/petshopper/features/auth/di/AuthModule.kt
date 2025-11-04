package com.example.petshopper.features.auth.di

import com.example.petshopper.features.auth.data.api.AuthApiService
import com.example.petshopper.features.auth.data.source.LocalAuthDataSource
import com.example.petshopper.features.auth.data.source.LocalAuthDataSourceImpl
import com.example.petshopper.features.auth.domain.repository.AuthRepository
import com.example.petshopper.features.auth.domain.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl : AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindLocalAuthDataSource(impl: LocalAuthDataSourceImpl): LocalAuthDataSource

    companion object{
        @Provides
        @Singleton
        fun bindAuthApiService(retrofit: Retrofit): AuthApiService {
            return retrofit.create(AuthApiService::class.java)
        }
    }
}