package com.example.petshopper.features.auth.domain.repository

import com.example.petshopper.features.auth.data.api.AuthApiService
import com.example.petshopper.features.auth.data.dto.LoginRequestDto
import com.example.petshopper.features.auth.data.dto.LoginResponseDto
import com.example.petshopper.features.auth.data.dto.LogoutRequestDto
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService
): AuthRepository {
    override suspend fun login(request: LoginRequestDto): LoginResponseDto {
        return apiService.login(request = request)
    }

    override suspend fun logout(request: LogoutRequestDto): Response<Unit> {
        return apiService.logout(request = request)
    }
}