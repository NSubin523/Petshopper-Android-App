package com.example.petshopper.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.petshopper.core.util.constants.state.UiState
import com.example.petshopper.features.auth.domain.usecase.LoginUseCase
import com.example.petshopper.features.auth.presentation.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.petshopper.core.domain.model.UserModel
import com.example.petshopper.features.auth.data.dto.LoginRequestDto
import com.example.petshopper.features.auth.data.dto.LogoutRequestDto
import com.example.petshopper.features.auth.domain.usecase.CheckLoginStatusUseCase
import com.example.petshopper.features.auth.domain.usecase.GetCurrentUserUseCase
import com.example.petshopper.features.auth.domain.usecase.LogoutUseCase
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val checkLoginStatusUseCase: CheckLoginStatusUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(UiState.Initial)
    val loginState: StateFlow<LoginUiState> = _loginState

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    private val _currentUser = MutableStateFlow<UserModel?>(null)
    val currentUser: StateFlow<UserModel?> = _currentUser

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            _isLoggedIn.value = checkLoginStatusUseCase()

            // Load user data if logged in
            if (_isLoggedIn.value == true) {
                _currentUser.value = getCurrentUserUseCase()
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            try {
                // Use case handles API call + saving auth data
                val response = loginUseCase(
                    LoginRequestDto(
                        email = email,
                        password = password
                    )
                )

                // Update UI state
                _currentUser.value = getCurrentUserUseCase()
                _isLoggedIn.value = true
                _loginState.value = UiState.Success(response)
            } catch (ex: Exception) {
                _loginState.value = UiState.Error(ex.message ?: "Unexpected error during login. Please try again")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            // Use case handles API call + clearing auth data
            logoutUseCase(
                LogoutRequestDto(
                    uuid = _currentUser.value?.uuid
                )
            )

            // Update UI state
            _currentUser.value = null
            _isLoggedIn.value = false
            _loginState.value = UiState.Initial
        }
    }

    fun resetLoginState() {
        _loginState.value = UiState.Initial
    }
}