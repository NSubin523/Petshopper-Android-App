package com.example.petshopper.features.auth.presentation.state

sealed interface LoginUiEvent {
    data class OnLoginClicked(val email: String, val password: String): LoginUiEvent
    data object OnSignUpClicked: LoginUiEvent
    data object OnForgotPasswordClicked: LoginUiEvent
    data object OnGoogleSignUpClicked: LoginUiEvent
}
