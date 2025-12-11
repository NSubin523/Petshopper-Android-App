package com.example.petshopper.features.bottomnavigation.profile.presentation.viewmodel

import com.example.petshopper.core.presentation.BaseViewModel
import com.example.petshopper.features.bottomnavigation.profile.data.dto.UpdateUserRequestDto
import com.example.petshopper.features.bottomnavigation.profile.domain.usecase.UpdateUserUseCase
import com.example.petshopper.features.bottomnavigation.profile.presentation.state.EditableFieldUserProfile
import com.example.petshopper.features.bottomnavigation.profile.presentation.state.ProfileAction
import com.example.petshopper.features.bottomnavigation.profile.presentation.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase
): BaseViewModel<ProfileState, ProfileAction>(ProfileState()){
    override fun onAction(action: ProfileAction) {
       when(action) {
           is ProfileAction.UpdateUserInformation -> {
               updateUserInformation(action)
           }
           is ProfileAction.ResetState -> {
               updateState {
                   it.copy(isLoading = false, isUpdateSuccessful = false, error = null)
               }
           }
       }
    }

    private fun updateUserInformation(action: ProfileAction.UpdateUserInformation) = launchAsync(
        onError = {
            updateState { it.copy(isLoading = false, error = it.error) }
        }
    ) {
        updateState { it.copy(isLoading = true, error = null) }

        val requestDto = when(action.field) {
            EditableFieldUserProfile.FirstName -> UpdateUserRequestDto(firstName = action.value)
            EditableFieldUserProfile.LastName -> UpdateUserRequestDto(lastName = action.value)
            EditableFieldUserProfile.Email -> UpdateUserRequestDto(email = action.value)
            EditableFieldUserProfile.PhoneNumber -> UpdateUserRequestDto(phoneNumber = action.value)
            else -> {
                UpdateUserRequestDto()
            }
        }

        updateUserUseCase(userId = action.userId, updateUserDto = requestDto)

        updateState { it.copy(isLoading = false, isUpdateSuccessful = true) }
    }
}