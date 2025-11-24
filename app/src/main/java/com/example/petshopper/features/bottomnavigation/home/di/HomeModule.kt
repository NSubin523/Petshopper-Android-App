package com.example.petshopper.features.bottomnavigation.home.di

import com.example.petshopper.features.bottomnavigation.home.data.api.CategoryApiService
import com.example.petshopper.features.bottomnavigation.home.data.api.InventoryApiService
import com.example.petshopper.features.bottomnavigation.home.data.repository.CategoryRepository
import com.example.petshopper.features.bottomnavigation.home.data.repository.CategoryRepositoryImpl
import com.example.petshopper.features.bottomnavigation.home.data.repository.InventoryRepository
import com.example.petshopper.features.bottomnavigation.home.data.repository.InventoryRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindInventoryRepository(impl: InventoryRepositoryImpl): InventoryRepository

    companion object{
        @Provides
        @Singleton
        fun bindCategoryApiService(retrofit: Retrofit): CategoryApiService {
            return retrofit.create(CategoryApiService::class.java)
        }

        @Provides
        @Singleton
        fun bindInventoryApiService(retrofit: Retrofit): InventoryApiService {
            return retrofit.create(InventoryApiService::class.java)
        }
    }
}