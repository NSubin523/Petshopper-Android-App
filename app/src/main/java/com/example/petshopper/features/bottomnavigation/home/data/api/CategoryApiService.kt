package com.example.petshopper.features.bottomnavigation.home.data.api

import com.example.petshopper.features.bottomnavigation.home.data.dto.CategoryResponseDto
import retrofit2.http.GET

interface CategoryApiService {

    @GET("categories")
    suspend fun getCategories(): List<CategoryResponseDto>

}