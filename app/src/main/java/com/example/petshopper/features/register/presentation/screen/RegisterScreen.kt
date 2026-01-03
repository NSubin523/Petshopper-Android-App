package com.example.petshopper.features.register.presentation.screen

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.ExperimentalAnimatableApi
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.core.util.constants.state.UiState
import com.example.petshopper.features.register.data.dto.CreateUserRequestDto
import com.example.petshopper.features.register.presentation.state.CreateUserAction
import com.example.petshopper.features.register.presentation.viewmodel.CreateUserViewModel

@OptIn(ExperimentalAnimatableApi::class, ExperimentalAnimationApi::class)
@Composable
fun RegisterScreen(
    viewModel: CreateUserViewModel = hiltViewModel(),
    onRegistrationComplete: () -> Unit = {}
) {
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsState()
    var step by remember { mutableStateOf(Constants.RegistrationStep.NAME) }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    var showCancelDialog by remember { mutableStateOf(false) }

    val isNextEnabled = when (step) {
        Constants.RegistrationStep.NAME -> firstName.isNotBlank() && lastName.isNotBlank()
        Constants.RegistrationStep.CREDENTIALS -> email.isNotBlank() && password.length >= 6 && password == confirmPassword
        Constants.RegistrationStep.PHONE -> phoneNumber.isNotBlank()
        Constants.RegistrationStep.CONFIRM -> true
    }

    LaunchedEffect(uiState.registerState) {
        when (val state = uiState.registerState) {
            is UiState.Success -> {
                Toast.makeText(context, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show()
                onRegistrationComplete()
                viewModel.onAction(CreateUserAction.ClearError)
            }
            is UiState.Error -> {
                Toast.makeText(context, "Registration failed: ${state.message}", Toast.LENGTH_LONG).show()
            }
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .animateContentSize(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {

            if (showCancelDialog) {
                AlertDialog(
                    onDismissRequest = { showCancelDialog = false },
                    title = { Text("Cancel Registration?") },
                    text = { Text("Are you sure you want to cancel the registration process?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showCancelDialog = false
                                onRegistrationComplete()
                            }
                        ) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showCancelDialog = false }
                        ) {
                            Text("No")
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { showCancelDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF635BFF)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Back to Login", color = Color.White)
                    }
                }

                Spacer(Modifier.height(8.dp))

                AnimatedContent(
                    targetState = step,
                    transitionSpec = {
                        (slideInHorizontally { it } + fadeIn()).togetherWith(slideOutHorizontally { -it } + fadeOut())
                    },
                    label = "stepTransition"
                ) { currentStep ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (currentStep) {
                            Constants.RegistrationStep.NAME -> {
                                Text("Let's start with your name", style = MaterialTheme.typography.titleMedium)
                                OutlinedTextField(
                                    value = firstName,
                                    onValueChange = { firstName = it },
                                    label = { Text("First Name") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = lastName,
                                    onValueChange = { lastName = it },
                                    label = { Text("Last Name") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Constants.RegistrationStep.CREDENTIALS -> {
                                Text("Now your credentials", style = MaterialTheme.typography.titleMedium)
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text("Password") },
                                    singleLine = true,
                                    visualTransformation = PasswordVisualTransformation(),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = confirmPassword,
                                    onValueChange = { confirmPassword = it },
                                    label = { Text("Confirm Password") },
                                    singleLine = true,
                                    visualTransformation = PasswordVisualTransformation(),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Constants.RegistrationStep.PHONE -> {
                                Text("What's your phone number?", style = MaterialTheme.typography.titleMedium)
                                OutlinedTextField(
                                    value = phoneNumber,
                                    onValueChange = { phoneNumber = it },
                                    label = { Text("Phone Number") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Constants.RegistrationStep.CONFIRM -> {
                                Text("Confirm your details", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(8.dp))
                                Text("$firstName $lastName", style = MaterialTheme.typography.bodyLarge)
                                Text(email, style = MaterialTheme.typography.bodyLarge)
                                Text(phoneNumber, style = MaterialTheme.typography.bodyLarge)
                                Spacer(Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        viewModel.onAction(
                                            CreateUserAction.Submit(
                                                CreateUserRequestDto(
                                                    firstName = firstName,
                                                    lastName = lastName,
                                                    email = email,
                                                    password = password,
                                                    phoneNumber = phoneNumber
                                                )
                                            )
                                        )
                                    },
                                    enabled = !uiState.isLoading,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    if (uiState.isLoading) {
                                        CircularProgressIndicator(
                                            color = Color.White,
                                            modifier = Modifier.size(20.dp),
                                            strokeWidth = 2.dp
                                        )
                                    } else {
                                        Text("Register")
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (step != Constants.RegistrationStep.NAME) {
                        TextButton(onClick = {
                            step = Constants.RegistrationStep.entries.toTypedArray()[step.ordinal - 1]
                        }) { Text("Back") }
                    } else {
                        Spacer(Modifier.width(1.dp))
                    }

                    if (step != Constants.RegistrationStep.CONFIRM) {
                        Button(
                            onClick = {
                                step = Constants.RegistrationStep.entries.toTypedArray()[step.ordinal + 1]
                            },
                            enabled = isNextEnabled
                        ) {
                            Text("Next")
                        }
                    }
                }
            }
        }
    }
}