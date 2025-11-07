package com.example.petshopper.features.register.presentation.viewmodel


import com.example.petshopper.core.presentation.BaseViewModel
import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.core.util.constants.state.UiState
import com.example.petshopper.features.register.data.dto.CreateUserRequestDto
import com.example.petshopper.features.register.domain.usecase.CreateUserUseCase
import com.example.petshopper.features.register.presentation.state.CreateUserAction
import com.example.petshopper.features.register.presentation.state.CreateUserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
): BaseViewModel<CreateUserUiState, CreateUserAction>(
    initialState = CreateUserUiState()
) {
    override fun onAction(action: CreateUserAction) {
        when (action) {
            is CreateUserAction.Submit -> createUser(action.request)
            CreateUserAction.ClearError -> updateState { it.copy(errorMessage = null) }
        }
    }

    private fun createUser(request: CreateUserRequestDto) {
        launchAsync(onError = { e ->
            updateState { it.copy(isLoading = false, errorMessage = e.message) }
        }) {
            updateState { it.copy(isLoading = true) }

            val result = createUserUseCase(request)

            if (result.isSuccessful && result.code() == Constants.HttpStatus.CREATED) {
                updateState {
                    it.copy(
                        isLoading = false,
                        registerState = UiState.Success(Unit)
                    )
                }
            } else {
                updateState {
                    it.copy(
                        isLoading = false,
                        registerState = UiState.Error(result.message() ?: "Failed to create user. Please try again later.")
                    )
                }
            }
        }
    }

}