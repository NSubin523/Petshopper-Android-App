package com.example.petshopper.features.bottomnavigation.profile.data.api

import com.example.petshopper.features.bottomnavigation.profile.data.dto.UpdateUserRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UpdateUser {

    @POST("/users/{userId}")
    suspend fun updateUserInformation(
        @Path("userId") userId: String,
        @Body updateUserDto: UpdateUserRequestDto
    ): Response<Any>
}