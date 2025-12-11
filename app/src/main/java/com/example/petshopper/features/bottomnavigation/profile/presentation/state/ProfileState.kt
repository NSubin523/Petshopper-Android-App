package com.example.petshopper.features.bottomnavigation.profile.presentation.state

data class ProfileState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isUpdateSuccessful: Boolean = false
)

sealed interface ProfileAction{
    data class UpdateUserInformation(
        val userId: String,
        val field: EditableFieldUserProfile,
        val value: String
    ): ProfileAction

    object ResetState: ProfileAction
}

enum class EditableFieldUserProfile(val label: String, val key: String) {
    FirstName("First Name", "firstName"),
    LastName("Last Name", "lastName"),
    Email("Email", "email"),
    PhoneNumber("Phone Number", "phoneNumber"),
    Password("Edit Password", "password")
}
