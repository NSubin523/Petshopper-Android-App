package com.example.petshopper.features.bottomnavigation.home.data.dto

data class CategoryResponseDto(
    val id : String,
    val type: String,
    val description: String,
    val imageUrl: String?,
    val createdAt: String,
    val updatedAt: String
)
