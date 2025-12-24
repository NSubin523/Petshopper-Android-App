package com.example.petshopper.features.bottomnavigation.home.data.repository

import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    suspend fun getInventoryByCategoryFromNetwork(categoryId: String, page: Int = Constants.DEFAULT_PAGE_START): List<Pet>

    suspend fun getInventoryFromLocal(categoryId: String): Flow<List<Pet>>

    suspend fun saveInventoryToLocal(categoryId: String, pets: List<Pet>)
}