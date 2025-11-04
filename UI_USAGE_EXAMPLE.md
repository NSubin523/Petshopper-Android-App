# How to Use the Refactored AuthViewModel

## Before (Old Way) ❌
```kotlin
// UI had to call multiple functions
viewModel.login(email, password)
viewModel.logout()
viewModel.resetLoginState()

// UI observed multiple state flows
viewModel.loginState.collect { }
viewModel.isLoggedIn.collect { }
viewModel.currentUser.collect { }
```

## After (New Way with MVI Pattern) ✅

### In your Composable/Activity:
```kotlin
@Composable
fun LoginScreen(viewModel: AuthViewModel = hiltViewModel()) {
    // Observe SINGLE state flow
    val state by viewModel.state.collectAsState()

    Column {
        // Show loading indicator
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        // Show error message
        if (state.hasError) {
            Text(
                text = state.errorMessage ?: "Unknown error",
                color = Color.Red
            )
        }

        // Email and password inputs
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        // Login button - Single action call
        Button(
            onClick = {
                viewModel.onAction(AuthAction.Login(email, password))
            },
            enabled = !state.isLoading
        ) {
            Text("Login")
        }

        // Show user info if logged in
        if (state.isLoggedIn && state.currentUser != null) {
            Text("Welcome ${state.currentUser.name}!")

            Button(onClick = {
                viewModel.onAction(AuthAction.Logout)
            }) {
                Text("Logout")
            }
        }
    }

    // Reset error state after showing it
    LaunchedEffect(state.hasError) {
        if (state.hasError) {
            delay(3000) // Show error for 3 seconds
            viewModel.onAction(AuthAction.ResetLoginState)
        }
    }
}
```

## Benefits:

1. **Single Entry Point**: Only `onAction()` is public
   - Makes it clear what the UI can do
   - Easy to trace user interactions

2. **Single State Flow**: Only one flow to observe
   - No synchronization issues
   - Single source of truth

3. **Testable Actions**: Actions are data classes
   ```kotlin
   @Test
   fun `test login with valid credentials`() {
       val viewModel = AuthViewModel(...)
       viewModel.onAction(AuthAction.Login("test@test.com", "password"))

       // Assert state changes
       assertEquals(true, viewModel.state.value.isLoggedIn)
   }
   ```

4. **Type Safety**: Compiler helps you
   - Can't forget required parameters
   - IDE autocomplete shows all possible actions

5. **Scalable**: Easy to add new features
   ```kotlin
   sealed class AuthAction {
       // Just add new actions here
       data object ForgotPassword : AuthAction()
       data class Register(val email: String, val password: String) : AuthAction()
   }
   ```

## Architecture Flow:

```
UI (Composable)
    ↓ triggers
AuthAction (sealed class)
    ↓ passed to
AuthViewModel.onAction()
    ↓ calls
Use Cases (business logic)
    ↓ uses
Repository (data management)
    ↓ updates
AuthUiState (single state object)
    ↓ observed by
UI (Composable)
```

This is proper MVI (Model-View-Intent) architecture! 🎉
