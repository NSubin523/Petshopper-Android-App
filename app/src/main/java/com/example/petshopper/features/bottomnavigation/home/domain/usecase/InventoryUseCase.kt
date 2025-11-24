package com.example.petshopper.features.bottomnavigation.home.domain.usecase

import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.features.bottomnavigation.home.data.repository.InventoryRepository
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet
import javax.inject.Inject

class InventoryUseCase @Inject constructor(
    private val inventoryRepository: InventoryRepository
) {

    suspend operator fun invoke(categoryId: String, page: Int = Constants.DEFAULT_PAGE_START): List<Pet>
        = inventoryRepository.getInventoryByCategory(
            categoryId = categoryId,
            page = page
        )
}