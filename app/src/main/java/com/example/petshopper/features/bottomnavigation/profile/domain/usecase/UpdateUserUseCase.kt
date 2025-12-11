package com.example.petshopper.features.bottomnavigation.profile.domain.usecase

import com.example.petshopper.features.bottomnavigation.profile.data.dto.UpdateUserRequestDto
import com.example.petshopper.features.bottomnavigation.profile.domain.repository.UpdateUserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UpdateUserRepository
) {
    suspend operator fun invoke(userId: String, updateUserDto: UpdateUserRequestDto) = repository.updateUserInformation(userId, updateUserDto)
}