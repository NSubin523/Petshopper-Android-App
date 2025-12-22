package com.example.petshopper.features.bottomnavigation.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet

@Entity("pets")
data class PetEntity(
    @PrimaryKey val id: String,
    val categoryId: String,
    val name: String,
    val imageUrl: String,
    val zipCode: String,
    val priceMin: Double,
    val priceMax: Double,
    val age: Int,
    val gender: String,
    val isAvailable: Boolean
) {
    fun toDomain() = Pet(
        id = id,
        name = name,
        imageUrl = imageUrl,
        zipCode = zipCode,
        priceMin = priceMin,
        priceMax = priceMax,
        age = age,
        gender = gender,
        isAvailable = isAvailable
    )
}

fun Pet.toEntity(categoryId: String) = PetEntity(
    id = id,
    categoryId = categoryId,
    name = name,
    imageUrl = imageUrl,
    zipCode = zipCode,
    priceMin = priceMin,
    priceMax = priceMax,
    age = age,
    gender = gender,
    isAvailable = isAvailable
)
