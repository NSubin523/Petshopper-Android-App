package com.example.petshopper.features.auth.presentation.state

import com.example.petshopper.core.domain.model.UserModel
import com.example.petshopper.core.util.constants.state.UiState
import com.example.petshopper.features.auth.data.dto.LoginResponseDto

typealias LoginUiState = UiState<LoginResponseDto>

/**
 * Complete UI state for Auth feature
 * Single source of truth for the Auth screen
 */
data class AuthUiState(
    val loginState: LoginUiState = UiState.Initial,
    val currentUser: UserModel? = null,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingSplash: Boolean = true,
) {
    /**
     * Helper to check if we're in an error state
     */
    val hasError: Boolean
        get() = loginState is UiState.Error

    /**
     * Get error message if in error state
     */
    val errorMessage: String?
        get() = (loginState as? UiState.Error)?.message
}
