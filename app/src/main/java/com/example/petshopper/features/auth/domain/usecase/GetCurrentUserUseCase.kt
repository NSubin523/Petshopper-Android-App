package com.example.petshopper.features.auth.domain.usecase

import com.example.petshopper.core.domain.model.UserModel
import com.example.petshopper.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): UserModel? {
        return authRepository.getCurrentUser()
    }
}
