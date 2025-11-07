package com.example.petshopper.features.bottomnavigation.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petshopper.features.bottomnavigation.home.domain.usecase.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
): ViewModel(){

    init {
        loadCategories()
    }

    fun loadCategories(){
        viewModelScope.launch {
            categoryUseCase()
        }
    }

}