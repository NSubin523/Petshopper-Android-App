package com.example.petshopper.features.bottomnavigation.home.data.repository

import com.example.petshopper.core.di.IoDispatcher
import com.example.petshopper.features.bottomnavigation.home.data.api.InventoryApiService
import com.example.petshopper.features.bottomnavigation.home.domain.mapper.PetMapper
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val apiService: InventoryApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): InventoryRepository {
    override suspend fun getInventoryByCategory(
        categoryId: String,
        page: Int
    ): List<Pet> {
        return withContext(ioDispatcher){
            apiService
                .getInventoryByCategory(categoryId = categoryId, page = page)
                .items.map { item ->
                    PetMapper.toDomain(item)
                }
        }
    }
}