package com.example.petshopper.features.bottomnavigation.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.petshopper.core.presentation.BaseViewModel
import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.features.bottomnavigation.home.domain.mapper.CategoryMapper
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

    private fun loadCategories() = launchAsync(onError = { error ->
        updateState { it.copy(isLoading = false, error = error.message) }
    }) {
        updateState { it.copy(isLoading = true, error = null) }

        val response = categoryUseCase()
        val categories = response.map {
            CategoryMapper.toEntity(it)
        }
        val firstPetId = categories.firstOrNull()?.id

        updateState { current ->
            current.copy(
                isLoading = false,
                categories = categories,
                selectedCategoryId = firstPetId
            )
        }

        selectedCategoryFlow.value = firstPetId
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSelectedCategory(){
        viewModelScope.launch {
            selectedCategoryFlow
                .filterNotNull()
                .flatMapLatest { categoryUuid ->
                    flow {
                        updateState {
                            it.copy(
                                isPetLoading = true,
                                currentPage = Constants.DEFAULT_PAGE_START,
                                pageEndReached = false,
                                pets = emptyList()
                            )
                        }
                        try {
                            val items = inventoryUseCase(categoryUuid, Constants.DEFAULT_PAGE_START)
                            emit(items)
                        } catch (ex: Exception){
                            Log.d("Error loading pets", ex.message.toString())
                            emit(emptyList())
                        }
                    }
                }
                .collect { pets ->
                    updateState { it.copy(
                        isPetLoading =  false,
                        pets = pets,
                        pageEndReached = pets.size < pageSize
                    ) }
                }
        }
    }

    private fun refreshInventory() {
        val selectedCategoryId = currentState.selectedCategoryId ?: return

        viewModelScope.launch {
            updateState { it.copy(isRefreshing = true) }
            try {
                val pets = inventoryUseCase(selectedCategoryId, Constants.DEFAULT_PAGE_START)
                updateState {
                    it.copy(
                        isRefreshing = false,
                        pets = pets,
                        currentPage = Constants.DEFAULT_PAGE_START,
                        pageEndReached = pets.size < pageSize
                    )
                }
            } catch (e: Exception) {
                Log.d("Error while refreshing inventory page", e.message.toString())
                updateState { it.copy(isRefreshing = false, error = e.message) }
            }
        }
    }

    private fun loadNextSetOfPets() {
        val currentState = currentState
        val categoryId = currentState.selectedCategoryId ?: return

        if(currentState.isPaginating || currentState.pageEndReached) return

        viewModelScope.launch {
            updateState { it.copy(isPaginating = true) }
            try {
                val nextPage = currentState.currentPage + 1
                val newPets = inventoryUseCase(categoryId = categoryId, page = nextPage)

                updateState { state ->
                    state.copy(
                        isPaginating = false,
                        currentPage = nextPage,
                        pets = state.pets + newPets,
                        pageEndReached = newPets.size < pageSize
                    )
                }
            }
            catch (ex: Exception){
                updateState { it.copy(isPaginating = false, error = ex.message) }
            }
        }
    }
}