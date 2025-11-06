package com.example.petshopper.features.auth.presentation.action

/**
 * Sealed class representing all possible user actions in the Auth feature
 * This follows the MVI (Model-View-Intent) pattern
 */
sealed class AuthAction {
    /**
     * User wants to login with email and password
     */
    data class Login(val email: String, val password: String) : AuthAction()

    /**
     * User wants to logout
     */
    data object Logout : AuthAction()

    /**
     * Check if user is logged in and load current user data
     */
    data object CheckLoginStatus : AuthAction()

    /**
     * Reset the login state to initial (e.g., after showing error)
     */
    data object ResetLoginState : AuthAction()
}
