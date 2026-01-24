package com.example.petshopper.features.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.petshopper.core.domain.model.UserModel
import com.example.petshopper.core.presentation.BaseViewModel
import com.example.petshopper.core.util.constants.IsEmulator
import com.example.petshopper.core.util.constants.state.UiState
import com.example.petshopper.features.auth.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.petshopper.features.auth.data.dto.LoginRequestDto
import com.example.petshopper.features.auth.data.dto.LoginResponseDto
import com.example.petshopper.features.auth.data.dto.LogoutRequestDto
import com.example.petshopper.features.auth.domain.usecase.CheckLoginStatusUseCase
import com.example.petshopper.features.auth.domain.usecase.GetCurrentUserUseCase
import com.example.petshopper.features.auth.domain.usecase.LogoutUseCase
import com.example.petshopper.features.auth.presentation.action.AuthAction
import com.example.petshopper.features.auth.presentation.state.AuthUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for Auth feature following MVI pattern
 *
 * Single entry point: onAction(AuthAction)
 * Single state flow: state (AuthUiState)
 *
 * Benefits:
 * - Testable: Actions are data classes, easy to test
 * - Clear intent: UI clearly expresses what it wants to do
 * - Single source of truth: One state object
 * - Scalable: Easy to add new actions
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val checkLoginStatusUseCase: CheckLoginStatusUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    @param:IsEmulator private val isEmulator: Boolean
) : BaseViewModel<AuthUiState, AuthAction>(
    initialState = AuthUiState()
) {

    init {
        // Check login status on initialization
        if(isEmulator){
            handleEmulatorByPass()
        } else {
            onAction(AuthAction.CheckLoginStatus)
        }
    }

    /**
     * Single entry point for all UI actions
     * This is the ONLY public function the UI should call
     */
    override fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.Login -> handleLogin(action.email, action.password)
            AuthAction.Logout -> handleLogout()
            AuthAction.CheckLoginStatus -> handleCheckLoginStatus()
            AuthAction.ResetLoginState -> handleResetLoginState()
        }
    }

    /**
     * Handle user login action
     */
    private fun handleLogin(email: String, password: String) {
        launchAsync(
            onError = { error ->
                updateState {
                    it.copy(
                        loginState = UiState.Error(
                            error.message ?: "Unexpected error during login. Please try again"
                        ),
                        isLoading = false
                    )
                }
            }
        ) {
            // Set loading state
            updateState { it.copy(loginState = UiState.Loading, isLoading = true) }

            // Use case handles API call + saving auth data
            val response = loginUseCase(
                LoginRequestDto(
                    email = email,
                    password = password
                )
            )

            // Get updated user data
            val user = getCurrentUserUseCase()

            // Update state with success
            updateState {
                it.copy(
                    loginState = UiState.Success(response),
                    currentUser = user,
                    isLoggedIn = true,
                    isLoading = false
                )
            }
        }
    }

    /**
     * Handle user logout action
     */
    private fun handleLogout() {
        launchAsync {
            logoutUseCase(
                LogoutRequestDto(
                    uuid = currentState.currentUser?.uuid
                )
            )

            updateState { it.copy(isLoading = true) }

            delay(2000)

            updateState {
                it.copy(isLoading = false, isLoggedIn = false)
            }
        }
    }

    /**
     * Handle check login status action
     */
    private fun handleCheckLoginStatus() {
        launchAsync {
            val isLoggedIn = checkLoginStatusUseCase()

            if (isLoggedIn) {
                val user = getCurrentUserUseCase()
                updateState {
                    it.copy(
                        currentUser = user,
                        isLoggedIn = true,
                        isLoadingSplash = false
                    )
                }
            } else {
                updateState {
                    it.copy(
                        currentUser = null,
                        isLoggedIn = false,
                        isLoadingSplash = false
                    )
                }
            }
        }
    }

    /**
     * Handle reset login state action
     */
    private fun handleResetLoginState() {
        updateState {
            it.copy(loginState = UiState.Initial)
        }
    }

    /**
     * Handle login by pass if the device is a registered emulator
     */
    private fun handleEmulatorByPass(){
        if (isEmulator) {
            viewModelScope.launch {
                delay(500)

                val mockedUser = UserModel(
                    uuid = "1234sdfKNSNdffdfa1223",
                    firstName = "Emulator",
                    lastName = "User",
                    email = "emulator@android.com",
                    phoneNumber = "0000000000",
                    createdAt = "2026-01-01 11:59:00"
                )

                // 2. Force the state to Logged In
                updateState {
                    it.copy(
                        isLoggedIn = true,
                        isLoadingSplash = false,
                        currentUser = mockedUser,
                        loginState = UiState.Success(
                            LoginResponseDto(
                                user = mockedUser,
                                "mock_emulator_jwt_token",
                                refreshToken = ""
                            )
                        )
                    )
                }
            }
        }
    }
}
