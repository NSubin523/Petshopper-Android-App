package com.example.petshopper.features.bottomnavigation.home.di

import com.example.petshopper.features.bottomnavigation.home.data.api.CategoryApiService
import com.example.petshopper.features.bottomnavigation.home.data.repository.CategoryRepository
import com.example.petshopper.features.bottomnavigation.home.data.repository.CategoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl : CategoryRepositoryImpl): CategoryRepository

    companion object{
        @Provides
        @Singleton
        fun bindCategoryApiService(retrofit: Retrofit): CategoryApiService {
            return retrofit.create(CategoryApiService::class.java)
        }
    }
}