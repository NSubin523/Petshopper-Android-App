package com.example.petshopper.features.bottomnavigation.home.data.repository

import com.example.petshopper.core.di.IoDispatcher
import com.example.petshopper.features.bottomnavigation.home.data.api.CategoryApiService
import com.example.petshopper.features.bottomnavigation.home.data.dto.CategoryResponseDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: CategoryApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): CategoryRepository {
    override suspend fun getCategories(): List<CategoryResponseDto> =
        withContext(ioDispatcher) {
            apiService.getCategories()
        }
    }
