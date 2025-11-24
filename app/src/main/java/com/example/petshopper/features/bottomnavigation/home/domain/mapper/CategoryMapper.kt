package com.example.petshopper.features.bottomnavigation.home.domain.mapper

import com.example.petshopper.features.bottomnavigation.home.data.dto.CategoryResponseDto
import com.example.petshopper.features.bottomnavigation.home.domain.model.Categories

object CategoryMapper {
    fun toEntity(dto: CategoryResponseDto): Categories {
        return Categories(
            id = dto.id,
            type = dto.type,
            description = dto.description,
            imageUrl = dto.imageUrl
        )
    }
}