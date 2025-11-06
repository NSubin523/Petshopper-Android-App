package com.example.petshopper.features.register.data.repository

import com.example.petshopper.features.register.data.dto.CreateUserRequestDto
import retrofit2.Response

interface CreateUserRepository {

    suspend fun createUser(newUser: CreateUserRequestDto): Response<Unit>

}