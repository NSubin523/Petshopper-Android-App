package com.example.petshopper.features.bottomnavigation

/**
 * All UI Events for landing screen will go here
 * This will include events in Home, Cart or Profile Section
 * We can map these events to the respective events of the screens as well
 */
sealed interface LandingUiEvent{
    data object OnNavigateToAccountInfoScreen: LandingUiEvent
}
