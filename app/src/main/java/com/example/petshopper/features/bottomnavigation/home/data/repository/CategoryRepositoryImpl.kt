package com.example.petshopper.features.bottomnavigation.home.data.repository

import com.example.petshopper.features.bottomnavigation.home.data.api.CategoryApiService
import com.example.petshopper.features.bottomnavigation.home.data.local.dao.CategoryDao
import com.example.petshopper.features.bottomnavigation.home.data.local.entity.toEntity
import com.example.petshopper.features.bottomnavigation.home.domain.mapper.CategoryMapper
import com.example.petshopper.features.bottomnavigation.home.domain.model.Categories
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: CategoryApiService,
    private val categoryDao: CategoryDao
): CategoryRepository {

    override suspend fun getCategoriesFromNetwork(): List<Categories> {
        return apiService.getCategories().map { dto -> CategoryMapper.toEntity(dto) }
    }

    override suspend fun getCategoriesFromLocal(): List<Categories> {
        return categoryDao.getAllCategories().map { it.toDomain() }
    }

    override suspend fun saveCategoriesToLocal(categories: List<Categories>) {
        categoryDao.insertCategories(categories = categories.map { it.toEntity() })
    }
}
