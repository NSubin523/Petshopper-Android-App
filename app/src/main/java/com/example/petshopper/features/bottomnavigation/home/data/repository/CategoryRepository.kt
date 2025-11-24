package com.example.petshopper.features.bottomnavigation.home.data.repository

import com.example.petshopper.features.bottomnavigation.home.data.dto.CategoryResponseDto

interface CategoryRepository {
    suspend fun getCategories(): List<CategoryResponseDto>
}