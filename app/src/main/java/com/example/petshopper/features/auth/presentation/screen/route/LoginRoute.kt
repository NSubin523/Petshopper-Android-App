package com.example.petshopper.features.auth.presentation.screen.route

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.petshopper.app.navigation.event.LoginNavEvent
import com.example.petshopper.core.util.constants.state.UiState
import com.example.petshopper.features.auth.presentation.action.AuthAction
import com.example.petshopper.features.auth.presentation.state.LoginUiEvent
import com.example.petshopper.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun LoginRoute(
    authViewModel: AuthViewModel,
    onNavigate: (LoginNavEvent) -> Unit
){
    val authState by authViewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.loginState) {
        when(val state = authState.loginState){
            is UiState.Success -> {
                Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                onNavigate(LoginNavEvent.OnLoginSuccess)
                authViewModel.onAction(AuthAction.ResetLoginState)
            }
            is UiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            else -> Unit
        }
    }

    LoginScreen(
        isLoading = authState.isLoading is UiState.Loading,
        onEvent = {event ->
            when(event){
                is LoginUiEvent.OnLoginClicked -> {
                    authViewModel.onAction(AuthAction.Login(event.email, event.password))
                }
                is LoginUiEvent.OnSignUpClicked -> {
                    onNavigate(LoginNavEvent.OnSignUpClicked)
                }
                else -> Unit
            }
        }
    )
}