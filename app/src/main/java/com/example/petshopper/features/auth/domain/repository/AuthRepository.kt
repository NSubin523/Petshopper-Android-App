package com.example.petshopper.features.auth.domain.repository

import com.example.petshopper.features.auth.data.dto.LoginRequestDto
import com.example.petshopper.features.auth.data.dto.LoginResponseDto
import com.example.petshopper.features.auth.data.dto.LogoutRequestDto
import retrofit2.Response

interface AuthRepository {
    suspend fun login(request: LoginRequestDto): LoginResponseDto

    suspend fun logout(request: LogoutRequestDto): Response<Unit>
}