package com.example.petshopper.features.bottomnavigation.profile.data.repositoryimpl

import com.example.petshopper.features.bottomnavigation.profile.data.api.UpdateUser
import com.example.petshopper.features.bottomnavigation.profile.data.dto.UpdateUserRequestDto
import com.example.petshopper.features.bottomnavigation.profile.domain.repository.UpdateUserRepository
import javax.inject.Inject

class UpdateUserRepositoryImpl @Inject constructor(
    private val api: UpdateUser
) : UpdateUserRepository {
    override suspend fun updateUserInformation(
        userId: String,
        updateUserDto: UpdateUserRequestDto
    )  = api.updateUserInformation(userId = userId, updateUserDto = updateUserDto)
}