package com.example.petshopper.features.bottomnavigation.home.presentation.state

import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.features.bottomnavigation.home.domain.model.Categories
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet

data class HomeState(
    val isLoading: Boolean = false,
    val categories: List<Categories> = emptyList(),
    val error: String? = null,
    val selectedCategoryId: String? = null,
    val isPetLoading: Boolean = false,
    val pets: List<Pet> = emptyList(),
    val searchQuery: String = "",
    val isRefreshing: Boolean = false,

    val isPaginating: Boolean = false,
    val currentPage: Int = Constants.DEFAULT_PAGE_START,
    val pageEndReached: Boolean = false,
)

sealed class HomeAction {
    object LoadCategories: HomeAction()
    data class SelectCategory(val id: String): HomeAction()
    data class OnSearchQueryChange(val query: String): HomeAction()
    object OnSearchClicked: HomeAction()
    object Refresh: HomeAction()
    object LoadMorePets: HomeAction()
}
