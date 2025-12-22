package com.example.petshopper.features.bottomnavigation.home.data.repository

import com.example.petshopper.features.bottomnavigation.home.data.api.InventoryApiService
import com.example.petshopper.features.bottomnavigation.home.data.local.dao.InventoryDao
import com.example.petshopper.features.bottomnavigation.home.data.local.entity.toEntity
import com.example.petshopper.features.bottomnavigation.home.domain.mapper.PetMapper
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val apiService: InventoryApiService,
    private val inventoryDao: InventoryDao,
): InventoryRepository {
    override suspend fun getInventoryByCategoryFromNetwork(
        categoryId: String,
        page: Int
    ): List<Pet> {
        return apiService.getInventoryByCategory(categoryId = categoryId, page = page)
            .items.map { dto -> PetMapper.toDomain(dto) }
    }

    override suspend fun getInventoryFromLocal(categoryId: String): List<Pet> {
        return inventoryDao.getPetsByCategory(categoryId = categoryId)
            .map { it.toDomain() }
    }

    override suspend fun saveInventoryToLocal(
        categoryId: String,
        pets: List<Pet>
    ) {
        val entities = pets.map { it.toEntity(categoryId = categoryId) }

        inventoryDao.insertPets(entities)
    }
}