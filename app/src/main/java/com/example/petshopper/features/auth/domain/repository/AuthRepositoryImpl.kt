package com.example.petshopper.features.auth.domain.repository

import com.example.petshopper.core.domain.model.UserModel
import com.example.petshopper.features.auth.data.api.AuthApiService
import com.example.petshopper.features.auth.data.dto.LoginRequestDto
import com.example.petshopper.features.auth.data.dto.LoginResponseDto
import com.example.petshopper.features.auth.data.dto.LogoutRequestDto
import com.example.petshopper.features.auth.data.source.LocalAuthDataSource
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val localDataSource: LocalAuthDataSource
): AuthRepository {
    override suspend fun login(request: LoginRequestDto): LoginResponseDto {
        return apiService.login(request = request)
    }

    override suspend fun logout(request: LogoutRequestDto): Response<Unit> {
        return apiService.logout(request = request)
    }

    override suspend fun saveAuthData(token: String, user: UserModel) {
        localDataSource.saveJwt(token)
        localDataSource.saveUser(user)
    }

    override suspend fun clearAuthData() {
        localDataSource.clear()
    }

    override suspend fun isLoggedIn(): Boolean {
        return localDataSource.isLoggedIn()
    }

    override suspend fun getCurrentUser(): UserModel? {
        return localDataSource.getUser()
    }
}