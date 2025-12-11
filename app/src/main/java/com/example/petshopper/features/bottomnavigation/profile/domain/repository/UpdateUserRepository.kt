package com.example.petshopper.features.bottomnavigation.profile.domain.repository

import com.example.petshopper.features.bottomnavigation.profile.data.dto.UpdateUserRequestDto
import retrofit2.Response

interface UpdateUserRepository {
    suspend fun updateUserInformation(userId: String, updateUserDto: UpdateUserRequestDto): Response<Any>
}