package com.example.petshopper.features.bottomnavigation.profile.presentation.state

sealed interface ProfileUiEvent {
    data object OnAccountInfoClicked: ProfileUiEvent
    data object OnLogoutClicked: ProfileUiEvent
}