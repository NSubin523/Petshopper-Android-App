package com.example.petshopper.features.bottomnavigation.home.presentation.viewmodel

import com.example.petshopper.core.presentation.BaseViewModel
import com.example.petshopper.features.bottomnavigation.home.domain.mapper.CategoryMapper
import com.example.petshopper.features.bottomnavigation.home.domain.usecase.CategoryUseCase
import com.example.petshopper.features.bottomnavigation.home.presentation.state.HomeAction
import com.example.petshopper.features.bottomnavigation.home.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
): BaseViewModel<HomeState, HomeAction>(HomeState()){

    init {
        onAction(HomeAction.LoadCategories)
    }

    override fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadCategories -> loadCategories()

            is HomeAction.SelectCategory -> updateState {
                it.copy(selectedCategoryId = action.id)
            }
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

        updateState { current ->
            current.copy(
                isLoading = false,
                categories = categories,
                selectedCategoryId = categories.firstOrNull()?.id
            )
        }
    }

}