package com.example.petshopper.features.bottomnavigation.home.domain.model

data class Pet(
    val id: String,
    val name: String,
    val imageUrl: String,
    val zipCode: String,
    val priceMin: Double,
    val priceMax: Double,
    val age: Int,
    val gender: String,
    val isAvailable: Boolean
)
