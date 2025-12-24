package com.example.petshopper.features.bottomnavigation.home.data.repository

import androidx.room.Transaction
import com.example.petshopper.features.bottomnavigation.home.data.api.InventoryApiService
import com.example.petshopper.features.bottomnavigation.home.data.local.dao.InventoryDao
import com.example.petshopper.features.bottomnavigation.home.data.local.entity.PetEntity
import com.example.petshopper.features.bottomnavigation.home.data.local.entity.toEntity
import com.example.petshopper.features.bottomnavigation.home.domain.mapper.PetMapper
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun getInventoryFromLocal(categoryId: String): Flow<List<Pet>> {
        return inventoryDao.getPetsByCategory(categoryId = categoryId)
            .map { entities -> entities.map{ it.toDomain() }}
    }

    override suspend fun saveInventoryToLocal(
        categoryId: String,
        pets: List<Pet>
    ) {
        val entities = pets.map { it.toEntity(categoryId = categoryId) }

        inventoryDao.insertPets(entities)
    }

    @Transaction
    suspend fun refreshCategory(categoryId: String){
        val networkPets = apiService.getInventoryByCategory(categoryId)

        val entities  = networkPets.items.map { networkItem ->
            PetEntity(
                id = networkItem.uuid,
                name = networkItem.name,
                imageUrl = networkItem.imageUrl,
                categoryId = categoryId,
                zipCode = networkItem.zipCode,
                priceMax = networkItem.priceMax,
                priceMin = networkItem.priceMin,
                age = networkItem.age,
                gender = networkItem.gender,
                isAvailable = networkItem.availability.equals("AVAILABLE", ignoreCase = true)
            )
        }

        inventoryDao.deletePetsByCategoryId(categoryId)
        inventoryDao.insertPets(entities)
    }
}