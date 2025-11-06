package com.example.petshopper.features.auth.domain.repository

import com.example.petshopper.core.domain.model.UserModel
import com.example.petshopper.features.auth.data.dto.LoginRequestDto
import com.example.petshopper.features.auth.data.dto.LoginResponseDto
import com.example.petshopper.features.auth.data.dto.LogoutRequestDto
import retrofit2.Response

interface AuthRepository {
    // Remote operations
    suspend fun login(request: LoginRequestDto): LoginResponseDto
    suspend fun logout(request: LogoutRequestDto): Response<Unit>

    // Local operations
    suspend fun saveAuthData(token: String, user: UserModel)
    suspend fun clearAuthData()
    suspend fun isLoggedIn(): Boolean
    suspend fun getCurrentUser(): UserModel?
}