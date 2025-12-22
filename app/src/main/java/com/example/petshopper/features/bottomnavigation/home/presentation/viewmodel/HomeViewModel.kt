package com.example.petshopper.features.bottomnavigation.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.petshopper.core.presentation.BaseViewModel
import com.example.petshopper.core.presentation.Resource
import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.features.bottomnavigation.home.domain.model.Categories
import com.example.petshopper.features.bottomnavigation.home.domain.usecase.CategoryUseCase
import com.example.petshopper.features.bottomnavigation.home.domain.usecase.InventoryUseCase
import com.example.petshopper.features.bottomnavigation.home.presentation.state.HomeAction
import com.example.petshopper.features.bottomnavigation.home.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
    private val inventoryUseCase: InventoryUseCase
): BaseViewModel<HomeState, HomeAction>(HomeState()){

    private val selectedCategoryFlow = MutableStateFlow<String?>(null)
    private val pageSize = Constants.DEFAULT_PAGE_SIZE

    init {
        onAction(HomeAction.LoadCategories)
        observeSelectedCategory()
    }

    override fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadCategories -> loadCategories()

            is HomeAction.SelectCategory -> {
                updateState {
                    it.copy(selectedCategoryId = action.id)
                }

                selectedCategoryFlow.value = action.id
            }

            is HomeAction.OnSearchQueryChange -> {
                updateState { it.copy(searchQuery = action.query) }
            }

            is HomeAction.OnSearchClicked -> {
                // TODO
            }

            is HomeAction.Refresh -> refreshInventory()

            is HomeAction.LoadMorePets -> loadNextSetOfPets()
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        updateState { it.copy(isLoading = true, error = null) }
                    }
                    is Resource.Success -> {
                        val categories = result.data ?: emptyList()
                        handleCategoriesLoaded(categories)
                    }
                    is Resource.Error -> {
                        val cachedCategories = result.data ?: emptyList()

                        updateState { it.copy(isLoading = false, error = result.message, categories = cachedCategories) }

                        if(cachedCategories.isNotEmpty()){
                            handleCategoriesLoaded(cachedCategories)
                        }
                    }
                }
            }
        }
    }

    private fun handleCategoriesLoaded(categories: List<Categories>){
        updateState { current ->
            current.copy(
                isLoading = false,
                categories = categories,
                selectedCategoryId = current.selectedCategoryId ?: categories.firstOrNull()?.id
            )
        }

        if(currentState.selectedCategoryId == null && categories.isNotEmpty()){
            selectedCategoryFlow.value = categories.first().id
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSelectedCategory(){
        viewModelScope.launch {
            selectedCategoryFlow
                .filterNotNull()
                .flatMapLatest { categoryUuid ->
                    inventoryUseCase(categoryUuid, Constants.DEFAULT_PAGE_START)
                }
                .collect { result ->
                    when(result){
                        is Resource.Loading -> {
                            updateState {
                                it.copy(
                                    isPetLoading = true,
                                    currentPage = Constants.DEFAULT_PAGE_START,
                                    pageEndReached = false,
                                    pets = emptyList()
                                )
                            }
                        }
                        is Resource.Success -> {
                            val pets = result.data ?: emptyList()
                            updateState {
                                it.copy(
                                    isPetLoading = false,
                                    pets = pets,
                                    pageEndReached = pets.size < pageSize
                                )
                            }
                        }
                        is Resource.Error -> {
                            val cachedPets = result.data ?: emptyList()
                            updateState {
                                it.copy(
                                    isPetLoading = false,
                                    error = result.message,
                                    pets = cachedPets,
                                    pageEndReached = cachedPets.size < pageSize
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun refreshInventory() {
        val selectedCategoryId = currentState.selectedCategoryId ?: return

        viewModelScope.launch {
            inventoryUseCase(selectedCategoryId, Constants.DEFAULT_PAGE_START)
                .collect { result ->
                    when(result) {
                        is Resource.Loading -> {
                            updateState { it.copy(isRefreshing = true) }
                        }
                        is Resource.Success -> {
                            val pets = result.data ?: emptyList()
                            updateState {
                                it.copy(
                                    isRefreshing = false,
                                    pets = pets,
                                    currentPage = Constants.DEFAULT_PAGE_START,
                                    pageEndReached = pets.size < pageSize
                                )
                            }
                        }
                        is Resource.Error -> {
                            updateState {
                                it.copy(
                                    isRefreshing = false,
                                    error = result.message
                                )
                            }
                        }
                    }
                 }
        }
    }

    private fun loadNextSetOfPets() {
        val currentState = currentState
        val categoryId = currentState.selectedCategoryId ?: return

        if(currentState.isPaginating || currentState.pageEndReached) return

        val nextPage = currentState.currentPage + 1

        viewModelScope.launch {
            inventoryUseCase(categoryId, nextPage).collect { result ->
                when(result){
                    is Resource.Loading -> {
                        updateState { it.copy(isPaginating = true) }
                    }
                    is Resource.Success -> {
                        val newPets = result.data ?: emptyList()
                        updateState { state ->
                            state.copy(
                                isPaginating = false,
                                currentPage = nextPage,
                                pets = state.pets + newPets,
                                pageEndReached = newPets.size < pageSize
                            )
                        }
                    }
                    is Resource.Error -> {
                        updateState { it.copy(isPaginating = false, error = result.message) }
                    }
                }
            }
        }
    }
}