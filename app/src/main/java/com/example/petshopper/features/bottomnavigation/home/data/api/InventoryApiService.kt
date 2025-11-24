package com.example.petshopper.features.bottomnavigation.home.data.api

import com.example.petshopper.core.util.constants.Constants
import com.example.petshopper.features.bottomnavigation.home.data.dto.InventoryListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InventoryApiService{

    @GET("/inventory/{categoryId}")
    suspend fun getInventoryByCategory(
        @Path("categoryId") categoryId: String,
        @Query("page") page: Int = Constants.DEFAULT_PAGE_START,
        @Query("pageSize") pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): InventoryListResponseDto
}