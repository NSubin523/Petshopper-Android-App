package com.example.petshopper.features.bottomnavigation.home.data.repository

import com.example.petshopper.features.bottomnavigation.home.data.api.InventoryApiService
import com.example.petshopper.features.bottomnavigation.home.domain.mapper.PetMapper
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val apiService: InventoryApiService
): InventoryRepository {
    override suspend fun getInventoryByCategory(
        categoryId: String,
        page: Int
    ): List<Pet> {
        return apiService
            .getInventoryByCategory(categoryId = categoryId, page = page)
            .items.map { item ->
                PetMapper.toDomain(item)
            }
    }
}