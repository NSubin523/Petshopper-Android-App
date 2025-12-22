package com.example.petshopper.features.bottomnavigation.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.petshopper.features.bottomnavigation.home.domain.model.Categories

@Entity("categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val type: String,
    val description: String,
    val imageUrl: String?
) {
    fun toDomain() = Categories(
        id = id,
        type = type,
        description = description,
        imageUrl = imageUrl
    )
}

fun Categories.toEntity() = CategoryEntity(
    id = id,
    type = type,
    description = description,
    imageUrl = imageUrl
)


