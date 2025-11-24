package com.example.petshopper.features.bottomnavigation.home.domain.mapper

import com.example.petshopper.features.bottomnavigation.home.data.dto.PetDto
import com.example.petshopper.features.bottomnavigation.home.domain.model.Pet

object PetMapper {
    fun toDomain(dto: PetDto): Pet {
        return Pet(
            id = dto.uuid,
            name = dto.name,
            imageUrl = dto.imageUrl,
            zipCode = dto.zipCode,
            priceMin = dto.priceMin,
            priceMax = dto.priceMax,
            age = dto.age,
            gender = dto.gender,
            isAvailable = dto.availability.equals("AVAILABLE", ignoreCase = true)
        )
    }
}