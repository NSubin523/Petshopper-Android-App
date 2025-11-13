package com.example.petshopper.features.bottomnavigation.home.presentation.state

import com.example.petshopper.features.bottomnavigation.home.domain.model.Categories

data class HomeState(
    val isLoading: Boolean = false,
    val categories: List<Categories> = emptyList(),
    val error: String? = null,
    val selectedCategoryId: String? = null
)

sealed class HomeAction {
    object LoadCategories: HomeAction()

    data class SelectCategory(val id: String): HomeAction()
}
