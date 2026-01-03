package com.example.petshopper.app.navigation.event

sealed interface LoginNavEvent {
    data object OnLoginSuccess : LoginNavEvent
    data object OnSignUpClicked : LoginNavEvent
    data object OnForgotPasswordClicked : LoginNavEvent
    data object OnGoogleSignInClicked : LoginNavEvent
}