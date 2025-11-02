package com.example.petshopper.features.auth.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.petshopper.core.util.constants.state.UiState
import com.example.petshopper.features.auth.domain.usecase.LoginUseCase
import com.example.petshopper.features.auth.presentation.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.petshopper.core.data.AuthDataStore
import com.example.petshopper.core.domain.model.UserModel
import com.example.petshopper.features.auth.data.dto.LoginRequestDto
import com.example.petshopper.features.auth.data.dto.LogoutRequestDto
import com.example.petshopper.features.auth.data.mapper.LoginMapper
import com.example.petshopper.features.auth.domain.usecase.LogoutUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    @param:ApplicationContext private val context: Context
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
            val jwt = AuthDataStore.getJwt(context = context)
            _isLoggedIn.value = !jwt.isNullOrEmpty()

            // Load user data if logged in
            if (_isLoggedIn.value == true) {
                val user = AuthDataStore.getUser(context = context)
                _currentUser.value = user
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            try {
                val response = loginUseCase(
                    LoginRequestDto(
                        email = email,
                        password = password
                    )
                )

                // Map DTO to UserModel
                val loggedInUser : UserModel = LoginMapper.toUserEntity(response)

                // Save JWT token and user data
                AuthDataStore.saveJwt(context = context, token = response.jwtToken)
                AuthDataStore.saveUser(context = context, user = loggedInUser)

                _currentUser.value = loggedInUser
                _isLoggedIn.value = true
                _loginState.value = UiState.Success(response)
            } catch (ex: Exception) {
                _loginState.value = UiState.Error(ex.message ?: "Unexpected error during login. Please try again")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                val response = logoutUseCase(
                    LogoutRequestDto(
                        uuid = _currentUser.value?.uuid
                    )
                )

                if (response.isSuccessful && response.code() == 204) {
                    android.util.Log.d("AuthViewModel", "Logout successful for user with uuid : " + _currentUser.value?.uuid)
                } else {
                    android.util.Log.w(
                        "AuthViewModel",
                        "Logout returned unexpected status: ${response.code()} - ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                // Log error but still proceed with local logout
                android.util.Log.e(
                    "AuthViewModel",
                    "Logout API call failed: ${e.message}",
                    e
                )
            } finally {
                // Always clear local data regardless of API response
                AuthDataStore.clear(context = context)
                _currentUser.value = null
                _isLoggedIn.value = false
                _loginState.value = UiState.Initial
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = UiState.Initial
    }
}