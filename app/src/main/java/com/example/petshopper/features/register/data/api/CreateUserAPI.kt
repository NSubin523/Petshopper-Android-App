package com.example.petshopper.features.register.data.api

import com.example.petshopper.features.register.data.dto.CreateUserRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateUserAPI {

    @POST("users")
    suspend fun createUser(@Body request: CreateUserRequestDto): Response<Unit>
}