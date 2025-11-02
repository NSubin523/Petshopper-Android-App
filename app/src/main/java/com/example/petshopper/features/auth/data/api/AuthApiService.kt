package com.example.petshopper.features.auth.data.api

import com.example.petshopper.features.auth.data.dto.LoginRequestDto
import com.example.petshopper.features.auth.data.dto.LoginResponseDto
import com.example.petshopper.features.auth.data.dto.LogoutRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @POST("auth/logout")
    suspend fun logout(@Body request: LogoutRequestDto): Response<Unit>
}