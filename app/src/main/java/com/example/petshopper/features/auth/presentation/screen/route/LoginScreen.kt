package com.example.petshopper.features.auth.presentation.screen.route

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import com.example.petshopper.features.auth.presentation.screen.composables.LoginInputField
import com.example.petshopper.features.auth.presentation.screen.composables.PetShopperActionButton
import com.example.petshopper.features.auth.presentation.state.LoginUiEvent
@Composable
fun LoginScreen(
    isLoading: Boolean,
    onEvent: (LoginUiEvent) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login to your account",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Hello, welcome back to your account",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                    textAlign = TextAlign.Center
                )

                LoginInputField(
                    value = email,
                    onValueChange = { email = it },
                    label = "E-mail",
                    placeholder = "example@email.com",
                    leadingIcon = Icons.Default.Email,
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                LoginInputField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    placeholder = "Your Password",
                    leadingIcon = Icons.Default.Lock,
                    visualTransformation = PasswordVisualTransformation(),
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { onEvent(LoginUiEvent.OnForgotPasswordClicked) },
                        enabled = !isLoading
                    ) {
                        Text(
                            text = "Forgot Password?",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF3F51B5))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                PetShopperActionButton(
                    text = "Login",
                    onClick = { onEvent(LoginUiEvent.OnLoginClicked(email, password)) },
                    isLoading = isLoading,
                    enabled = email.isNotBlank() && password.isNotBlank()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text("  or sign up with  ")
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { onEvent(LoginUiEvent.OnGoogleSignUpClicked) },
                        enabled = !isLoading
                    ) {
                        Icon(Icons.Default.Email, contentDescription = "Google")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Don't have an account?")
                    TextButton(
                        onClick = { onEvent(LoginUiEvent.OnSignUpClicked) },
                        enabled = !isLoading
                    ) {
                        Text("Sign up")
                    }
                }
            }
        }
    }
}