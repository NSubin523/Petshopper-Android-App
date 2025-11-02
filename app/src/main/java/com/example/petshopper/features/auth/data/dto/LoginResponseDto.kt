package com.example.petshopper.features.auth.data.dto

import com.example.petshopper.core.domain.model.UserModel

data class LoginResponseDto(
    val user: UserModel,
    val jwtToken: String,
    val refreshToken: String?
)
