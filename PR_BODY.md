## Summary

This PR refactors the authentication module to follow proper Clean Architecture principles and implements the MVI (Model-View-Intent) pattern for better state management.

### 🏗️ Architecture Improvements

#### 1. Fixed Clean Architecture Violations
- **Before**: AuthViewModel directly accessed AuthDataStore (data layer)
- **After**: Proper layering: ViewModel → UseCase → Repository → DataSource

**Changes:**
- Created `LocalAuthDataSource` interface to abstract data storage
- Enhanced `AuthRepository` to manage both API and local data
- Updated UseCases to orchestrate complete auth flows
- Removed all data layer dependencies from ViewModel

#### 2. Implemented MVI Pattern
- **Before**: Multiple public functions, multiple state flows
- **After**: Single `onAction()` entry point, single state flow

**New Components:**
- `BaseViewModel<STATE, ACTION>` - Reusable base for all ViewModels
- `AuthAction` sealed class - Type-safe user intents
- `AuthUiState` data class - Single source of truth for UI state

### 📦 What's New

#### BaseViewModel (`core/presentation/BaseViewModel.kt`)
```kotlin
abstract class BaseViewModel<STATE, ACTION>(initialState: STATE)
```
- Generic base class for all ViewModels
- Thread-safe state management with `updateState()`
- Built-in error handling with `launchAsync()`
- Enforces single entry point pattern

#### AuthAction (`features/auth/presentation/action/AuthAction.kt`)
```kotlin
sealed class AuthAction {
    data class Login(email, password)
    data object Logout
    data object CheckLoginStatus
    data object ResetLoginState
}
```

#### Enhanced AuthUiState
- Single data class containing all UI state
- Helper properties: `hasError`, `errorMessage`
- Immutable with copy-based updates

#### Refactored AuthViewModel
- Extends `BaseViewModel<AuthUiState, AuthAction>`
- Single entry point: `onAction(AuthAction)`
- Single state flow: `state: StateFlow<AuthUiState>`
- Clean, testable, maintainable

### ✅ Benefits

1. **Proper Layering**
   - ViewModel no longer depends on data layer
   - Business logic in UseCases, not ViewModel
   - Easy to unit test without Android Context

2. **Better State Management**
   - Single state flow simplifies UI observation
   - No synchronization issues
   - Predictable state updates

3. **Testability**
   - Actions are data classes - easy to test
   - Single state to assert
   - Mockable dependencies

4. **Scalability**
   - BaseViewModel reduces boilerplate
   - Easy to add new actions
   - Reusable pattern across features

5. **Type Safety**
   - Compiler enforces correct usage
   - Clear contract between UI and ViewModel

### 🔄 Migration Required

**Before:**
```kotlin
viewModel.login(email, password)
viewModel.loginState.collect { }
viewModel.isLoggedIn.collect { }
```

**After:**
```kotlin
viewModel.onAction(AuthAction.Login(email, password))
viewModel.state.collect { state ->
    // state.loginState, state.isLoggedIn, state.currentUser
}
```

See `UI_USAGE_EXAMPLE.md` for detailed usage examples.

### 📁 Files Changed

**New Files:**
- `core/presentation/BaseViewModel.kt`
- `features/auth/data/source/LocalAuthDataSource.kt`
- `features/auth/data/source/LocalAuthDataSourceImpl.kt`
- `features/auth/domain/usecase/CheckLoginStatusUseCase.kt`
- `features/auth/domain/usecase/GetCurrentUserUseCase.kt`
- `features/auth/presentation/action/AuthAction.kt`
- `UI_USAGE_EXAMPLE.md`

**Modified Files:**
- `features/auth/presentation/viewmodel/AuthViewModel.kt`
- `features/auth/presentation/state/AuthUIState.kt`
- `features/auth/domain/repository/AuthRepository.kt`
- `features/auth/domain/repository/AuthRepositoryImpl.kt`
- `features/auth/domain/usecase/LoginUseCase.kt`
- `features/auth/domain/usecase/LogoutUseCase.kt`
- `features/auth/di/AuthModule.kt`

### 🧪 Testing

- All existing functionality preserved
- No breaking changes to business logic
- UI layer needs migration to new pattern

### 📚 Related

This establishes a foundation for:
- Other feature modules to use BaseViewModel
- Consistent MVI pattern across the app
- Better developer experience and code quality

---

**Commits:**
1. `462e0d0` - Fix Clean Architecture violations in AuthViewModel
2. `401d71a` - Implement MVI pattern with BaseViewModel and Actions
