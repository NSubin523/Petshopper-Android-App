package com.example.petshopper.features.bottomnavigation.home.data.repository

import com.example.petshopper.features.bottomnavigation.home.domain.model.Categories

interface CategoryRepository {
    suspend fun getCategoriesFromNetwork(): List<Categories>

    suspend fun getCategoriesFromLocal(): List<Categories>

    suspend fun saveCategoriesToLocal(categories: List<Categories>)
}