package com.example.petshopper.features.bottomnavigation.home.domain.usecase

import com.example.petshopper.features.bottomnavigation.home.data.repository.CategoryRepository
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke() = repository.getCategories()

}