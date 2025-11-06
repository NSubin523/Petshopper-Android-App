package com.example.petshopper.features.auth.domain.usecase

import com.example.petshopper.core.domain.model.UserModel
import com.example.petshopper.features.auth.data.dto.LoginRequestDto
import com.example.petshopper.features.auth.data.dto.LoginResponseDto
import com.example.petshopper.features.auth.data.mapper.LoginMapper
import com.example.petshopper.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(request: LoginRequestDto): LoginResponseDto {
        // Call API
        val response = authRepository.login(request = request)

        // Map to domain model
        val user: UserModel = LoginMapper.toUserEntity(response)

        // Save auth data locally
        authRepository.saveAuthData(token = response.jwtToken, user = user)

        return response
    }
}