package com.example.petshopper.features.auth.domain.usecase

import com.example.petshopper.features.auth.data.dto.LogoutRequestDto
import com.example.petshopper.features.auth.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(request: LogoutRequestDto): Response<Unit> = authRepository.logout(request = request)
}