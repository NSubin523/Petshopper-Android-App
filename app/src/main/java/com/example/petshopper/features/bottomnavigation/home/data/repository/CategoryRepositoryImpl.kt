package com.example.petshopper.features.bottomnavigation.home.data.repository

import com.example.petshopper.features.bottomnavigation.home.data.api.CategoryApiService
import com.example.petshopper.features.bottomnavigation.home.data.dto.CategoryResponseDto
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: CategoryApiService
): CategoryRepository {
    override suspend fun getCategories(): List<CategoryResponseDto> = apiService.getCategories()
}