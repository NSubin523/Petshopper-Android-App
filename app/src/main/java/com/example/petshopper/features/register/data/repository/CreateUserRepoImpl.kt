package com.example.petshopper.features.register.data.repository

import com.example.petshopper.features.register.data.api.CreateUserAPI
import com.example.petshopper.features.register.data.dto.CreateUserRequestDto
import retrofit2.Response
import javax.inject.Inject

class CreateUserRepoImpl @Inject constructor(
    private val apiService: CreateUserAPI
): CreateUserRepository{
    override suspend fun createUser(newUser: CreateUserRequestDto): Response<Unit> {
        return apiService.createUser(newUser)
    }

}