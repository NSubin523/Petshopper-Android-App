package com.example.petshopper.features.register.presentation.state

import com.example.petshopper.core.util.constants.state.UiState
import com.example.petshopper.features.register.data.dto.CreateUserRequestDto

data class CreateUserUiState(
    val registerState: UiState<Unit> = UiState.Initial,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val hasError: Boolean get() = registerState is UiState.Error
}

sealed class CreateUserAction {
    data class Submit(val request: CreateUserRequestDto) : CreateUserAction()
    object ClearError : CreateUserAction()
}