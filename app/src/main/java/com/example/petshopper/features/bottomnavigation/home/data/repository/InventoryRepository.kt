package com.example.petshopper.features.bottomnavigation.home.data.repository

import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet

interface InventoryRepository {
    suspend fun getInventoryByCategory(categoryId: String, page: Int = Constants.DEFAULT_PAGE_START): List<Pet>
}