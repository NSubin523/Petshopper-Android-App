package com.example.petshopper.features.register.data.dto

data class CreateUserRequestDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String
)
